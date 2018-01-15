package net.sergey.kosov.market.domains

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Size

@Document(collection = "Goods")
data class Goods(var id: ObjectId = ObjectId(),
                 var title: String,
                 @Size(min = 10, max = 500) var description: String,
                 var title2: String? = null,
                 var enabled: Boolean = false)