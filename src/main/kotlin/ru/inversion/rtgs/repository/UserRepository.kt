package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.inversion.rtgs.entity.RtgsUser
import java.util.*

@Repository
//@Transactional
interface UserRepository: JpaRepository<RtgsUser, Long> {
    fun findRtgsUserByLogin(login: String) : Optional<RtgsUser>
    fun findAllById(id: Long): MutableList<RtgsUser>

    @Query("select * from rtgs_user   where bank_id = :id order by CREATED_DATE desc",nativeQuery = true)
    fun findAllByBank_idEquals(id: Long) : MutableList<RtgsUser>
//    @Modifying
//    @Query("update rtgs_user set EName = :eName where id = :id",nativeQuery = true)
//    fun updateUser(id: Long, eName: String)
}
