package net.sergey.kosov.telegramsender.api

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "communication-service")
interface CommunicationApi {
    @RequestMapping(path = ["/completed/{messageId}"], method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    void completedMessage(@PathVariable("messageId") String messageId)
}
