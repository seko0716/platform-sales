package net.sergey.kosov.market.domains

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "orders")
data class Order(@Id var id: ObjectId = ObjectId(),
                 var goods: Goods,
                 var title: String = "Order ${goods.title}",
                 var description: String = "",
                 var count: Int = 1,
                 var customer: User,
                 @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
                 var createdTime: LocalDateTime = LocalDateTime.now(),
                 var status: Status = Status.CREATED,
                 var statusHistory: MutableList<Pair<Status, LocalDateTime>> = mutableListOf(status to createdTime),
                 @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
                 var submittedTime: LocalDateTime? = null,
                 var messageThreadId: ObjectId? = null) {

    fun changeStatus(status: Status): Order {
        if (status == Status.CREATED) throw IllegalStateException("Нельзя сетить статус $status, он заполняется только при создании ордера")
        return this.apply {
            this.status = status
            this.statusHistory.add(Pair(status, LocalDateTime.now()))
        }
    }
}