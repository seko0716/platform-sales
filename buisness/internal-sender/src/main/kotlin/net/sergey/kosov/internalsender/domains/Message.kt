package net.sergey.kosov.internalsender.domains

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.common.serializers.ObjectIdSerializer
import net.sergey.kosov.common.utils.DateTimeUtils.Companion.dateTimeUtc
import org.bson.types.ObjectId
import java.time.LocalDateTime

@NoArgs
data class Message(@JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                   var mess: String,
                   var type: MessageType,
                   var to: List<String>,
                   var protocol: String,
                   @JsonSerialize(using = ObjectIdSerializer::class) var entityId: ObjectId? = null,
                   var from: String = "",
                   var accessToken: String = "",
                   var status: Status = Status.CREATED,
                   var creationDate: LocalDateTime = dateTimeUtc(),
                   var completedDate: LocalDateTime? = null)