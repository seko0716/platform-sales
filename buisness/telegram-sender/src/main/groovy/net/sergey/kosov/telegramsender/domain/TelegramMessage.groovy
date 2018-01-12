package net.sergey.kosov.telegramsender.domain

import groovy.transform.builder.Builder

@Builder
class TelegramMessage {
    String mess
    String to
    String from
    String accessToken
}
