package net.sergey.kosov.communication.domains

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
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
                   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                   var creationDate: LocalDateTime = LocalDateTime.now(),
                   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                   var completedDate: LocalDateTime? = null)

