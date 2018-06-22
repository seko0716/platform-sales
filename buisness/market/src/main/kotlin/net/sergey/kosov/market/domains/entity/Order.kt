package net.sergey.kosov.market.domains.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.common.serializers.ObjectIdSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@NoArgs
@Document(collection = "orders")
data class Order(@Id @JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                 var product: Product,
                 @Indexed(name = "orders_title")
                 var title: String = "Order ${product.title}",
                 var description: String = "",
                 var count: Int = 1,
                 var customer: User,
                 var createdTime: LocalDateTime = LocalDateTime.now(),
                 @Indexed(name = "orders_status")
                 var status: Status = Status.CREATED,
                 var statusHistory: MutableList<Pair<Status, LocalDateTime>> = mutableListOf(status to createdTime),
                 var completedTime: LocalDateTime? = null,
                 @JsonSerialize(using = ObjectIdSerializer::class) var messageThreadId: ObjectId? = null) {
    object _Order {
        const val ID = "id"
        const val product = "product"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val COUNT = "count"
        const val CUSTOMER = "customer"
        const val CREATED_TIME = "createdTime"
        const val STATUS = "status"
        const val STATUS_HISTORY = "statusHistory"
        const val COMPLETED_TIME = "completedTime"
        const val MESSAGE_THREAD_ID = "messageThreadId"
    }
}