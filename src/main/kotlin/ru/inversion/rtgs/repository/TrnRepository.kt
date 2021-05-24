package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.inversion.rtgs.entity.RtgsTrn
import java.time.LocalDate

@Repository
interface TrnRepository: JpaRepository<RtgsTrn, Long> {
    @Query("select * from rtgs_trn  where trunc(ED_DATE) = :dat", nativeQuery = true)
    fun getAllByDate(dat: LocalDate) : MutableList<RtgsTrn>
}
