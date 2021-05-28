package ru.inversion.rtgs.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.stereotype.Repository
import ru.inversion.rtgs.entity.RtgsTrn
import java.time.LocalDate

@Repository
interface TrnRepository: JpaRepository<RtgsTrn, Long> {
    @Query("select t.* from trn t where t.DTRNDOC = :dat and t.CTRNIDOPEN = :login order by t.DTRNDOC desc", nativeQuery = true)
//    @Query("select t.itrnnum,t.itrnanum,t.itrndocnum,t.ctrnmfoo, t.ctrnmfoa,t.itrntype,t.itrnpriority,t.mtrnsum, t.ctrnclient_acc, " +
//            "t.ctrnmfoo,t.ctrnaccd, t.ctrnacca, t.ctrnmfoa, t.ctrnaccc, t.ctrnidopen,t.ctrnpurp, t.ctrnclient_name, t.ctrnowna,t.dtrndoc , " +
//            "t.ctrnstate1, t.ctrncur from trn t where t.DTRNDOC = :dat and t.CTRNIDOPEN = :login order by t.DTRNDOC desc", nativeQuery = true)
    fun getAllByDate(dat: LocalDate,login: String) : MutableList<RtgsTrn>

    @Query("select t.* from trn t where t.ITRNNUM = :itrnnum and t.ITRNANUM = :itrnanum", nativeQuery = true)
//    @Query("select t.itrnnum,t.itrnanum,t.itrndocnum,t.ctrnmfoo, t.ctrnmfoa,t.itrntype,t.itrnpriority,t.mtrnsum, t.ctrnclient_acc, " +
//            "t.ctrnmfoo,t.ctrnaccd, t.ctrnacca, t.ctrnmfoa, t.ctrnaccc, t.ctrnidopen,t.ctrnpurp, t.ctrnclient_name, t.ctrnowna,t.dtrndoc , " +
//            "t.ctrnstate1, t.ctrncur from trn t where t.ITRNNUM = :itrnnum and t.ITRNANUM = :itrnanum", nativeQuery = true)
    fun getLastTransaction(itrnnum: Long? , itrnanum: Long? = 0) : RtgsTrn


}
