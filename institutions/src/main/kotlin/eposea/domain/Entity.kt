package eposea.domain

import com.sun.istack.NotNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table
@Entity
data class Institution(
    @Id val id: String,
    @NotNull @Column(nullable = false, unique = true) val title: String,
    @NotNull @Column(nullable = false, unique = true) val url: String
)
