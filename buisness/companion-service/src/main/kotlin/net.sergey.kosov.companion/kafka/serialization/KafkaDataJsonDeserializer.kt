package net.sergey.kosov.companion.kafka.serialization


import net.sergey.kosov.companion.domains.KafkaData
import org.springframework.kafka.support.serializer.JsonDeserializer

class KafkaDataJsonDeserializer : JsonDeserializer<KafkaData>()