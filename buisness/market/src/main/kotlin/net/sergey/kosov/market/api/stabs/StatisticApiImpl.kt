package net.sergey.kosov.market.api.stabs

import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.repository.product.ProductRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Profile("test")
@Component
class StatisticApiImpl(var productRepository: ProductRepository) : StatisticApi {
    override fun getChart(username: String?, chartSize: Int): List<String> {
        return productRepository.findByQuery(Query().limit(chartSize)).map { it.id.toString() }
    }
}