package net.sergey.kosov.communication.domains

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.google.gson.Gson
import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.common.serializers.ObjectIdSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@NoArgs
@Document
data class Message(@Id @JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                   var mess: String,
                   var type: MessageType,
                   var to: List<String>,
                   var protocol: String,
                   @JsonSerialize(using = ObjectIdSerializer::class) var entityId: ObjectId? = null,
                   var from: String = "",
                   var accessToken: String = "",
                   var status: Status = Status.CREATED,
                   var creationDate: LocalDateTime = LocalDateTime.now(),
                   var completedDate: LocalDateTime? = null) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}

@NoArgs
data class ViewMessageCreation(var mess: String,
                               var type: MessageType,
                               var to: String = "",
                               var protocol: String = "internal",
                               var entityId: String)

enum class MessageType {
    ORDER_COMMENT,
    PRODUCT_COMMENT,
    PERSONAL,
    MARKET_EVENT,
    INTERNAL_EVENT
}
