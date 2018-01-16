package net.sergey.kosov.market.domains

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "orders")
data class Order(@Id var id: ObjectId = ObjectId(),
                 var goods: Goods,
                 var count: Int = 1,
                 var customer: User,
                 @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
                 var createdTime: LocalDateTime = LocalDateTime.now(),
                 var status: Status = Status.CREATED,
                 @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
                 var submittedTime: LocalDateTime? = null,
                 var messageThreadId: ObjectId? = null) {

}