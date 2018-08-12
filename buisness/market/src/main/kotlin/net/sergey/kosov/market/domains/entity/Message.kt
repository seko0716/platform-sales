package net.sergey.kosov.market.domains.entity

import net.sergey.kosov.common.annotations.NoArgs

@NoArgs
data class Message(var mess: String,
                   var type: MessageType = MessageType.MARKET_EVENT,
                   var to: String = "",
                   var protocol: String = "internal",
                   var entityId: String)

