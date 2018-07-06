package net.sergey.kosov.account.domains

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import net.sergey.kosov.common.annotations.NoArgs
import net.sergey.kosov.common.serializers.ObjectIdSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@NoArgs
@Document(collection = "users")
data class User(@Id @JsonSerialize(using = ObjectIdSerializer::class) var id: ObjectId = ObjectId(),
                @Indexed(unique = true, name = "name_index")
                var name: String,
                var family: String,
                var account: Account,
                var fullName: String = "$name $family")