package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.inversion.rtgs.entity.RtgsTrn

interface TrnRepository: JpaRepository<RtgsTrn, Long> {
}
