package com.kolisnichenko2828.profiles.data.local

import com.kolisnichenko2828.profiles.domain.UsersDomain

fun UsersEntity.toDomain(): UsersDomain {
    return UsersDomain(
        id = this.id,
        name = this.name
    )
}

fun List<UsersEntity>.toDomain(): List<UsersDomain> {
    return this.map {
        it.toDomain()
    }
}