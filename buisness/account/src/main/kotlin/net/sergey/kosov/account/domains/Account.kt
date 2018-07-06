package net.sergey.kosov.account.domains

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.common.serializers.ObjectIdSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@NoArgs
@Document(collection = "accounts")
data class Account(@Id @JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                   @Indexed(unique = true, name = "market_name_index")
                   var marketName: String,
                   var description: String = "",
                   var images: List<String> = listOf())
