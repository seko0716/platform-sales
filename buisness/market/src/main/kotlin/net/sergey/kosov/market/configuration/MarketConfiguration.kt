package net.sergey.kosov.market.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.convert.Jsr310Converters

@Configuration
@EntityScan(value = ["net.sergey.kosov.market.domains"], basePackageClasses = [Jsr310Converters::class])
class MarketConfiguration