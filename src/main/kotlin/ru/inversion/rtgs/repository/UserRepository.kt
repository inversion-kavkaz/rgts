package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.inversion.rtgs.entity.RtgsUser
import java.util.*

interface UserRepository: JpaRepository<RtgsUser, Long> {
    fun findRtgsUserByLogin(login: String) : Optional<RtgsUser>
}
