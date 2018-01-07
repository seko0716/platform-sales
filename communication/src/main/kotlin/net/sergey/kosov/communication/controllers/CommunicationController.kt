package net.sergey.kosov.communication.controllers

import net.sergey.kosov.communication.services.CommunicationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/communication/")
class CommunicationController @Autowired constructor(var service: CommunicationService) {

    @PostMapping("/send")
    fun send(message: String, protocol: String, to: String) {
        service.createAndSend(message, protocol, to)
    }
}