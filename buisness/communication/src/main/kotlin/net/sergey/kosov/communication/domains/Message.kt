package net.sergey.kosov.communication.domains

import com.google.gson.Gson
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Message(@Id var id: ObjectId = ObjectId(),
                   var mess: String,
                   var to: String,
                   var from: String = "",
                   var accessToken: String = "",
                   var protocol: String,
                   var status: Status = Status.CREATED,
                   var creationDate: LocalDateTime = LocalDateTime.now(),
                   var completedDate: LocalDateTime? = null) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}

