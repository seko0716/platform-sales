package net.sergey.kosov.statistic.domains

import net.sergey.kosov.common.annotations.NoArgs
import org.bson.types.ObjectId
import java.time.LocalDateTime

@NoArgs
data class Order(var id: String,
                 var product: Product,
                 var title: String = "Order ${product.title}",
                 var count: Int = 1,
                 var customer: User,
                 var createdTime: LocalDateTime = LocalDateTime.now(),
                 var status: String,
                 var statusHistory: MutableList<StatusHistoryItem>,
                 var completedTime: LocalDateTime? = null,
                 var messageThreadId: ObjectId? = null) {

    @NoArgs
    data class StatusHistoryItem(var status: String, var timestamp: String)

}
