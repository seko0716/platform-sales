package net.sergey.kosov.statistic.companions.kafka.serialization

import net.sergey.kosov.statistic.domains.KafkaData
import org.springframework.kafka.support.serializer.JsonSerializer

class KafkaDataJsonSerializer : JsonSerializer<KafkaData>()