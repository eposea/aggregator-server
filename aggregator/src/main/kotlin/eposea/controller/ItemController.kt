package eposea.controller

import eposea.service.AggregatorService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/institutions/{institutionId}/items", produces = [MediaType.APPLICATION_JSON])
class ItemController(private val aggregatorService: AggregatorService) {

    @Get("/{itemId}")
    fun getItem(@PathVariable institutionId: String, @PathVariable itemId: String) =
        aggregatorService.getItem(institutionId, itemId)

}
