package net.sergey.kosov.market.utils

import org.bson.types.ObjectId

fun String.toObjectId(): ObjectId = ObjectId(this)