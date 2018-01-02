package net.sergey.kosov.registrationservice.domain

import groovy.transform.builder.Builder

@Builder
class Message {
    String mess
    String to
    String from
    String accessToken
}
