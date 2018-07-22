package net.sergey.kosov.communication.controllers


import net.sergey.kosov.communication.domains.Message
import net.sergey.kosov.communication.domains.ViewMessageCreation
import net.sergey.kosov.communication.services.MessageService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class CommunicationController @Autowired constructor(var service: MessageService) {

    @PostMapping("/send")
    fun send(@RequestBody message: ViewMessageCreation, principal: Principal): Message {
        return service.createAndSend(message, principal.name)
    }

    @GetMapping(path = ["/completed/{messageId}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @PreAuthorize("#oauth2.hasScope('server')")
    fun completedMessage(@PathVariable("messageId") messageId: String) {
        service.completeMessage(ObjectId(messageId))
    }
}