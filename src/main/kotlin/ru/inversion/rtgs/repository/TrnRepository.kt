package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.inversion.rtgs.entity.RtgsTrn

@Repository
interface TrnRepository: JpaRepository<RtgsTrn, Long> {
}
