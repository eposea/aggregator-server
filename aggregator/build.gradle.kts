import com.google.protobuf.gradle.*
import org.gradle.kotlin.dsl.invoke

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.kotlin.kapt") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("io.micronaut.application") version "3.2.0"
    id("com.google.protobuf") version "0.8.18"
}

version = "0.1"
group = "eposea"

val kotlinVersion = project.properties["kotlinVersion"]
val grpcVersion = "1.44.0"
val grpcKotlinVersion = "1.2.1"
val protobufVersion = "3.19.2"
val protocVersion = protobufVersion
val mapstructVersion = "1.5.0.Beta2"

repositories {
    mavenCentral()
}
dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.liquibase:micronaut-liquibase")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-stub:${grpcVersion}")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    implementation("io.micronaut:micronaut-validation")
    implementation("javax.inject:javax.inject:1")
    implementation("com.github.javafaker:javafaker:1.0.2") {
        exclude("org.yaml", "snakeyaml")
    }
    implementation("org.yaml:snakeyaml:1.30")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.slf4j:slf4j-simple")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}


application {
    mainClass.set("eposea.AggregatorKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("eposea.*")
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protocVersion}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDirs("src/common/proto")
        }
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/grpckt")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}
