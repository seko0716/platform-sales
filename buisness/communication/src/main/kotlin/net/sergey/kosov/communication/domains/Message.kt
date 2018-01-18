package net.sergey.kosov.communication.domains

import com.google.gson.Gson
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Message(@Id var id: ObjectId = ObjectId.get(), var mess: String, var to: String,
                   var from: String = "", var accessToken: String = "", var protocol: String, var status: Status = Status.CREATED) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}

