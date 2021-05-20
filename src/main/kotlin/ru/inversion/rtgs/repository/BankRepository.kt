package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.inversion.rtgs.entity.RtgsBank

interface BankRepository : JpaRepository<RtgsBank, Long> {
}
