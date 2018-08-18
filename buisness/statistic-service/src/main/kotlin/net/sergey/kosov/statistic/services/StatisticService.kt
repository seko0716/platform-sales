package net.sergey.kosov.statistic.services

import net.sergey.kosov.statistic.domains.Order
import net.sergey.kosov.statistic.repositories.ESRepository
import org.elasticsearch.index.query.QueryBuilders
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class StatisticService @Autowired constructor(val esRepository: ESRepository) {
    fun getRecentlyViewed(login: String?, httpSession: HttpSession): List<Map<String, Object>> {
        val sessionId = login ?: httpSession.id
        return esRepository.findByQuery(QueryBuilders.termQuery("sessionId.keyword", sessionId))
    }

    fun getCompanions(productId: String): List<Map<String, Object>> {
        return esRepository.findCompanionsById(id = productId)
    }

    fun orderAction(order: Order): Order {
        val orderProperties = properties(order)
        esRepository.insertStatistic(order.product.account, order.id, orderProperties)
        return order
    }

    fun properties(order: Order): MutableMap<String, Any> {
        return order::class.java.declaredFields.map { it.name to it.get(order) }.toMap().toMutableMap()
    }

}