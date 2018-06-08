package net.sergey.kosov.market.domains

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import javax.validation.constraints.Size

@Document(collection = "Products")
data class Product(@Id var id: ObjectId = ObjectId(),
                   @Indexed(name = "product_title")
                   var title: String,
                   @Size(min = 10, max = 500)
                   var description: String,
                   @Indexed(name = "product_accountId")
                   var accountId: ObjectId,
                   @Indexed(name = "product_price")
                   var price: BigDecimal,
                   var category: Category,
                   var characteristic: List<Characteristic> = category.characteristics,
                   var tags: List<String> = ArrayList(),
                   @Indexed(name = "product_enabled")
                   var enabled: Boolean = false)

