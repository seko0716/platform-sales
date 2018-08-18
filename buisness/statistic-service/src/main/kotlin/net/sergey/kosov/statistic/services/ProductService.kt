package net.sergey.kosov.statistic.services

import net.sergey.kosov.statistic.repositories.ESRepository
import org.elasticsearch.index.query.QueryBuilders
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class ProductService @Autowired constructor(val esRepository: ESRepository) {
    fun getRecentlyViewed(login: String?, httpSession: HttpSession): List<Map<String, Object>> {
        val sessionId = login ?: httpSession.id
        return esRepository.findByQuery(QueryBuilders.termQuery("sessionId", sessionId))
    }
}