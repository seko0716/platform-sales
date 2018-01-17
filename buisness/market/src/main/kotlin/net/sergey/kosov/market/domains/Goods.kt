package net.sergey.kosov.market.domains

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import javax.validation.constraints.Size

@Document(collection = "Goods")
data class Goods(var id: ObjectId = ObjectId(),
                 var title: String,
                 @Size(min = 10, max = 500) var description: String,
                 var accountId: ObjectId,
                 var price: BigDecimal,
                 var category: Category,
                 var characteristic: List<Characteristic> = category.characteristics,
                 var tags: List<String> = ArrayList(),
                 var enabled: Boolean = false)

