package net.sergey.kosov.market.repository

import org.springframework.data.mongodb.core.query.Query

interface RepositoryQuery<T> {
    fun findByQuery(query: Query): List<T>
}