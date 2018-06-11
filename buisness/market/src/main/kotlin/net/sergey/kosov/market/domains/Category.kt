package net.sergey.kosov.market.domains

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import net.sergey.kosov.common.ObjectIdSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Categories")
data class Category(@Id @JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                    @Indexed(name = "category_title")
                    var title: String,
                    var description: String = "",
                    @Indexed(name = "category_parentId")
                    var parentId: ObjectId? = null,
                    var characteristics: List<Characteristic> = ArrayList())
