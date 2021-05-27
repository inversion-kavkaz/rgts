package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.inversion.rtgs.entity.RtgsBank

@Repository
interface BankRepository : JpaRepository<RtgsBank, Long> {
}
