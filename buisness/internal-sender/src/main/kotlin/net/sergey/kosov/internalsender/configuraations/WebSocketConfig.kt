package net.sergey.kosov.internalsender.configuraations

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : AbstractWebSocketMessageBrokerConfigurer() {

    /**
     * Register Stomp endpoints: the url to open the WebSocket connection.
     */
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {

        // Register the "/ws" endpoint, enabling the SockJS protocol.
        // SockJS is used (both client and server side) to allow alternative
        // messaging options if WebSocket is not available.
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS()
    }


}