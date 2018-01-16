package net.sergey.kosov.market.domains

data class User(var name: String,
                var family: String,
                var fullName: String = "$name $family") {

}