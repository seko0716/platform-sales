package net.sergey.kosov.market.domains.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.common.serializers.ObjectIdSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import javax.validation.constraints.Size

@NoArgs
@Document(collection = "Products")
data class Product(@Id @JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                   @Indexed(name = "product_title")
                   var title: String,
                   @Size(min = 10, max = 500)
                   var description: String,
                   @Indexed(name = "product_account")
                   var account: Account,
                   @Indexed(name = "product_price")
                   var price: BigDecimal,
                   var category: Category,
                   var characteristic: List<Characteristic> = category.characteristics,
                   var tags: List<String> = listOf(),
                   @Indexed(name = "product_enabled")
                   var enabled: Boolean = false)

