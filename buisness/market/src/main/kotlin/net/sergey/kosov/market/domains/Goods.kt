package net.sergey.kosov.market.domains

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import javax.validation.constraints.Size

@Document(collection = "Goods")
data class Goods(@Id var id: ObjectId = ObjectId(),
                 @Indexed
                 var title: String,
                 @Indexed
                 @Size(min = 10, max = 500) var description: String,
                 var accountId: ObjectId,
                 @Indexed
                 var price: BigDecimal,
                 @Indexed
                 var category: Category,
                 var characteristic: List<Characteristic> = category.characteristics,
                 var tags: List<String> = ArrayList(),
                 @Indexed
                 var enabled: Boolean = false)

