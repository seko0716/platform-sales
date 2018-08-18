package net.sergey.kosov.statistic.repositories

import net.sergey.kosov.statistic.domains.Category
import net.sergey.kosov.statistic.domains.Characteristic
import net.sergey.kosov.statistic.domains.Product
import org.elasticsearch.action.search.SearchResponse
import org.omg.CORBA.Object
import java.math.BigDecimal

class DomainWrapper {
    fun toProduct(response: SearchResponse): List<Map<String, Object>> {
        return response.hits.hits.asSequence()
                .map {
                    @Suppress("UsePropertyAccessSyntax")
                    it.getSourceAsMap()
                }
//                .map { @Suppress("UNCHECKED_CAST") map2Product(it as Map<String, Any>) }
                .toList() as List<Map<String, Object>>
    }

    private fun map2Product(map: Map<String, Any>): Product {
        return Product(id = map["id"].toString(),
                title = map["title"].toString(),
                description = map["description"].toString(),
                account = map["account"].toString(),
                price = BigDecimal(map["price"].toString()),
                category = map2category(map["category"] as Map<String, Any>),
                characteristic = (map["characteristic"] as List<Map<String, Any>>).map { map2Characteristic(it) },
                tags = map["tags"] as List<String>,
                productInfo = map["productInfo"].toString(),
                enabled = map["enabled"] as Boolean,
                date = map["date"].toString()
        )
    }

    private fun map2Characteristic(map: Map<String, Any>): Characteristic {
        return Characteristic(name = map["name"].toString(),
                value = map["value"].toString())
    }

    private fun map2category(map: Map<String, Any>): Category {
        return Category(title = map["title"].toString(),
                description = map["description"].toString(),
                parent = map2category(map["parent"] as Map<String, Any>),
                characteristics = (map["characteristics"] as List<Map<String, Any>>).map { map2Characteristic(it) })
    }
}