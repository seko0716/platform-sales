package net.sergey.kosov.statistic.repositories

import net.sergey.kosov.statistic.configurations.ElasticSearchProperties
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.Client
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.search.sort.SortOrder
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ESRepository @Autowired constructor(val client: Client, val elasticSearchProperties: ElasticSearchProperties) {

    fun findByQuery(query: QueryBuilder, size: Int = elasticSearchProperties.searchSize): List<Map<String, Object>> {
        val searchResponse = findByQuery(query, size, "viewing_products", arrayOf("product"))
        return searchResponse.hits.hits.map { it.getSourceAsMap()["product"] as Map<String, Object> }
    }

    fun findCompanionsById(id: String): List<Map<String, Object>> {
        val searchResponse = client.prepareGet("companions", null, id)
                .setFetchSource("companions", null)
                .get().getSourceAsMap()["companions"] as List<Map<String, Object>>
        return searchResponse
    }


    protected fun findByQuery(query: QueryBuilder, size: Int, index: String, include: Array<String>, exclude: Array<String>? = null): SearchResponse {
        return client
                .prepareSearch(index)
                .setQuery(query)
                .setSize(size)
                .setFetchSource(include, exclude)
                .addSort("@timestamp", SortOrder.ASC)
                .get()
    }
}