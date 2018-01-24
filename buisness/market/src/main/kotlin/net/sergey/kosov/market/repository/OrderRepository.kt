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
import org.springframework.stereotype.Repository
@Repository
class OrderRepository @Autowired constructor(var orderRepository: _OrderRepository, var mongoTemplate: MongoTemplate) {

    fun find(query: Query): MutableList<Order> {
        return mongoTemplate.find(query, Order::class.java)
    }


    fun <S : Order?> insert(entity: S): S {
        return orderRepository.insert(entity)
    }

    fun <S : Order?> insert(entities: MutableIterable<S>?): MutableList<S> {
        return orderRepository.insert(entities)
    }

    fun <S : Order?> save(entites: MutableIterable<S>?): MutableList<S> {
        return orderRepository.save(entites)
    }

    fun <S : Order?> save(entity: S): S {
        return orderRepository.save(entity)
    }

    fun findOne(id: ObjectId?): Order {
        return orderRepository.findOne(id)
    }

    fun <S : Order?> findOne(example: Example<S>?): S {
        return orderRepository.findOne(example)
    }

    fun findAll(): MutableList<Order> {
        return orderRepository.findAll()
    }

    fun findAll(sort: Sort?): MutableList<Order> {
        return orderRepository.findAll(sort)
    }

    fun <S : Order?> findAll(example: Example<S>?): MutableList<S> {
        return orderRepository.findAll(example)
    }

    fun <S : Order?> findAll(example: Example<S>?, sort: Sort?): MutableList<S> {
        return orderRepository.findAll(example)
    }

    fun findAll(pageable: Pageable?): Page<Order> {
        return orderRepository.findAll(pageable)
    }

    fun findAll(ids: MutableIterable<ObjectId>?): MutableIterable<Order> {
        return orderRepository.findAll(ids)
    }

    fun <S : Order?> findAll(example: Example<S>?, pageable: Pageable?): Page<S> {
        return orderRepository.findAll(example, pageable)
    }

    fun count(): Long {
        return orderRepository.count()
    }

    fun <S : Order?> count(example: Example<S>?): Long {
        return orderRepository.count(example)
    }

    fun exists(id: ObjectId?): Boolean {
        return orderRepository.exists(id)
    }

    fun <S : Order?> exists(example: Example<S>?): Boolean {
        return orderRepository.exists(example)
    }

    fun deleteAll() {
        return orderRepository.deleteAll()
    }

    fun delete(id: ObjectId?) {
        return orderRepository.delete(id)
    }

    fun delete(entity: Order?) {
        return orderRepository.delete(entity)
    }

    fun delete(entities: MutableIterable<Order>?) {
        return orderRepository.delete(entities)
    }
}