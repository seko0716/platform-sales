package net.sergey.kosov.communication.domains

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.google.gson.Gson
import net.sergey.kosov.common.serializers.ObjectIdSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Message(@Id @JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                   var mess: String,
                   var to: String,
                   var protocol: String,
                   var from: String = "",
                   var accessToken: String = "",
                   var status: Status = Status.CREATED,
                   @JsonSerialize(using = ObjectIdSerializer::class) var orderId: ObjectId? = null,
                   @JsonSerialize(using = ObjectIdSerializer::class) var goodsId: ObjectId? = null,
                   var creationDate: LocalDateTime = LocalDateTime.now(),
                   var completedDate: LocalDateTime? = null) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}
