package net.sergey.kosov.market.api

import net.sergey.kosov.market.domains.entity.Message
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "communication-service")
interface CommunicationApi {

    @RequestMapping(path = ["/communication/send/internal"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun sendInternalMessage(message: Message)
}