package net.sergey.kosov.companion.kafka.serialization


import net.sergey.kosov.companion.domains.KafkaData
import org.springframework.kafka.support.serializer.JsonSerializer

class KafkaDataJsonSerializer : JsonSerializer<KafkaData>()