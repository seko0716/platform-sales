package net.sergey.kosov.common.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.bson.types.ObjectId


class ObjectIdSerializer : JsonSerializer<ObjectId>() {
    override fun serialize(value: ObjectId?, jgen: JsonGenerator, provider: SerializerProvider) {
        if (value == null) {
            jgen.writeNull()
        } else {
            jgen.writeString(value.toString())
        }
    }
}