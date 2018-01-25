package net.sergey.kosov.market.repository

import net.sergey.kosov.market.domains.Order

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
@Component
class OrderRepository @Autowired constructor(var crudOrderRepository: CrudOrderRepository, var mongoTemplate: MongoTemplate) {

    fun find(query: Query): MutableList<Order> {
        return mongoTemplate.find(query, Order::class.java)
    }


    fun <S : Order?> insert(entity: S): S {
        return crudOrderRepository.insert(entity)
    }

    fun <S : Order?> insert(entities: MutableIterable<S>?): MutableList<S> {
        return crudOrderRepository.insert(entities)
    }

    fun <S : Order?> save(entites: MutableIterable<S>?): MutableList<S> {
        return crudOrderRepository.save(entites)
    }

    fun <S : Order?> save(entity: S): S {
        return crudOrderRepository.save(entity)
    }

    fun findOne(id: ObjectId?): Order {
        return crudOrderRepository.findOne(id)
    }

    fun <S : Order?> findOne(example: Example<S>?): S {
        return crudOrderRepository.findOne(example)
    }

    fun findAll(): MutableList<Order> {
        return crudOrderRepository.findAll()
    }

    fun findAll(sort: Sort?): MutableList<Order> {
        return crudOrderRepository.findAll(sort)
    }

    fun <S : Order?> findAll(example: Example<S>?): MutableList<S> {
        return crudOrderRepository.findAll(example)
    }

    fun <S : Order?> findAll(example: Example<S>?, sort: Sort?): MutableList<S> {
        return crudOrderRepository.findAll(example)
    }

    fun findAll(pageable: Pageable?): Page<Order> {
        return crudOrderRepository.findAll(pageable)
    }

    fun findAll(ids: MutableIterable<ObjectId>?): MutableIterable<Order> {
        return crudOrderRepository.findAll(ids)
    }

    fun <S : Order?> findAll(example: Example<S>?, pageable: Pageable?): Page<S> {
        return crudOrderRepository.findAll(example, pageable)
    }

    fun count(): Long {
        return crudOrderRepository.count()
    }

    fun <S : Order?> count(example: Example<S>?): Long {
        return crudOrderRepository.count(example)
    }

    fun exists(id: ObjectId?): Boolean {
        return crudOrderRepository.exists(id)
    }

    fun <S : Order?> exists(example: Example<S>?): Boolean {
        return crudOrderRepository.exists(example)
    }

    fun deleteAll() {
        return crudOrderRepository.deleteAll()
    }

    fun delete(id: ObjectId?) {
        return crudOrderRepository.delete(id)
    }

    fun delete(entity: Order?) {
        return crudOrderRepository.delete(entity)
    }

    fun delete(entities: MutableIterable<Order>?) {
        return crudOrderRepository.delete(entities)
    }
}