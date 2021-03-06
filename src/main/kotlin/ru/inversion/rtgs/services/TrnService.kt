package ru.inversion.rtgs.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.inversion.rtgs.entity.RtgsTrn
import ru.inversion.rtgs.facade.TrnFacade
import ru.inversion.rtgs.payload.response.TrnAffirmResponse
import ru.inversion.rtgs.payload.response.TrnCreateResponse
import ru.inversion.rtgs.payload.request.TrnFilterRequest
import ru.inversion.rtgs.payload.request.TrnRequest
import ru.inversion.rtgs.payload.response.TrnFilterResponse
import ru.inversion.rtgs.repository.TrnRepository
import ru.inversion.rtgs.services.utils.CustomerRowMapper
import java.sql.CallableStatement
import java.time.LocalDate


@Service
class TrnService @Autowired constructor(
        private val trnRepository: TrnRepository,
        private val trnFacade: TrnFacade
) {

    /**
     * @author Dmitry Hvastunov
     * @created 21.05.2021
     * @project rtgs
     */

    val CALL_CREATE = "rtgs_package.reg_transaction(?,?,?,null,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
    val CALL_DELETE = "rtgs_package.remove_transaction(?)"
    val CALL_AFFIRM = "rtgs_package.affirm_transaction(?)"

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    fun create(trn: RtgsTrn): TrnCreateResponse {

        val connection = jdbcTemplate?.getDataSource()?.getConnection();
        val callableStatement: CallableStatement? = connection?.prepareCall("{call ? := ${CALL_CREATE}") ?: null
        callableStatement?.setInt(2, 2)
        callableStatement?.setInt(3, 0)
        callableStatement?.setString(4, trn.edDate?.toLocalDate().toString())
        callableStatement?.setString(5, trn.payerCorrespAcc)
        callableStatement?.setString(6, trn.payeeCorrespAcc)
        callableStatement?.setString(7, trn.payerPersonalAcc)
        callableStatement?.setString(8, trn.payerName)
        callableStatement?.setString(9, trn.payerPersonalAcc)
        callableStatement?.setString(10, trn.payeeName)
        callableStatement?.setString(11, trn.purpose)
        if (trn.edNo == null) trn.edNo = -1
        callableStatement?.setLong(12, trn.edNo!!)

        callableStatement?.setLong(13, trn.sum!!)

        callableStatement?.setString(14, trn.currency)
        callableStatement?.setString(15, trn.currency)
        callableStatement?.setInt(16, 0)
        callableStatement?.setString(17, trn.login)

        callableStatement?.registerOutParameter(1, java.sql.Types.VARCHAR)
        callableStatement?.registerOutParameter(18, java.sql.Types.DECIMAL)
        callableStatement?.registerOutParameter(19, java.sql.Types.DECIMAL)
        val resultSet = callableStatement?.executeUpdate()


        val result = callableStatement?.getString(1)
        val trnnum: Long? = callableStatement?.getLong(18)
        val trnanum: Long? = callableStatement?.getLong(19)

        connection?.close()

        if (result != null && result.startsWith("SUCCESS")) {
            return TrnCreateResponse(trnRepository.getLastTransaction(trnnum, trnanum), "Transaction is registered")
        } else return TrnCreateResponse(null, result)
    }

    fun update(trn: RtgsTrn): TrnCreateResponse {
        val resString: String?
        if (trn.itrnnum != null) {
            resString = delete(trn.itrnnum)
            if (!resString.isNullOrEmpty() && resString.startsWith("SUCCESS"))
                return create(trn)
        }
        return TrnCreateResponse(null, "Update error")
    }

    fun delete(trnId: Long): String? {
        val connection = jdbcTemplate?.getDataSource()?.getConnection();
        val callableStatement: CallableStatement? = connection?.prepareCall("{call ? := ${CALL_DELETE}") ?: null
        callableStatement?.setLong(2, trnId)
        callableStatement?.registerOutParameter(1, java.sql.Types.VARCHAR)
        val resultSet = callableStatement?.executeUpdate()

        val result = callableStatement?.getString(1)
        connection?.close()
        return result


    }

    fun getAllUserTrnByDate(trnReq: TrnRequest): MutableList<RtgsTrn>? {
        val userLogin = trnReq.login
        if (userLogin == null) return mutableListOf()
        var ld: LocalDate? = LocalDate.parse(trnReq.date)
        if (ld == null) ld = LocalDate.now()
        var res = mutableListOf<RtgsTrn>()
        if (ld != null) {
            res = trnRepository.getAllByDate(ld, userLogin)
        }
        return res
    }

    fun getAllUserTrnByFilter(trnReq: TrnFilterRequest): TrnFilterResponse {

        var filterString = ""
        val sortString = if(!trnReq.sort.isNullOrEmpty()) " order by ${convertColumnNames(trnReq.sort)}" else  ""


        val startDate = if(trnReq.filter.startDate != null) trnReq.filter.startDate.plusDays(1).toString().substring(0,10) else ""
        val endDate = if(trnReq.filter.endDate != null) trnReq.filter.endDate.plusDays(1).toString().substring(0,10) else ""

        if (!trnReq.filter.payerCorrespAcc.isNullOrEmpty()) filterString = " CTRNACCD = '${trnReq.filter.payerCorrespAcc}'"
        if (!trnReq.filter.payeeCorrespAcc.isNullOrEmpty()) filterString = " CTRNACCC = '${trnReq.filter.payeeCorrespAcc}'"
        if (!trnReq.filter.login.isNullOrEmpty()) filterString += " and CTRNIDOPEN = '${trnReq.filter.login}'"
        if (trnReq.filter.status != null) filterString += " and CTRNSTATE1 = ${trnReq.filter.status}"

        if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) filterString += " and DTRNDOC between to_date('${startDate}', 'yyyy-mm-dd') and to_date('${endDate}', 'yyyy-mm-dd')"


        var SQL = "select rownum r, t.* from (select * from trn ${if(!filterString.isEmpty()) "where" else ""}   ${filterString} ${sortString}) t "
        val SQL_WRAPPER = "select * from ( ${SQL} ) where r between ${trnReq.pageNum} * ${trnReq.pageSize} + 1 " +
                "and ((${trnReq.pageNum} + 1) * ${trnReq.pageSize}) "
        val SQL_COUNTER = "select count(*) from (${SQL})"
        val counter = jdbcTemplate?.queryForObject(SQL_COUNTER, Long::class.java)

        return  TrnFilterResponse(jdbcTemplate?.query(SQL_WRAPPER, CustomerRowMapper()) as List<RtgsTrn?>, counter)
    }

    private fun convertColumnNames(sort: String?): String? {
        return sort?.replace("purpose","CTRNPURP")
                ?.replace("status","CTRNSTATE1")
                ?.replace("edNo","ITRNDOCNUM")
                ?.replace("edDate","DTRNDOC")
                ?.replace("payeePersonalAcc","CTRNACCA")
                ?.replace("payerPersonalAcc","CTRNCLIENT_ACC")
                ?.replace("currency","CTRNCUR")
                ?.replace("payeeName","CTRNOWNA")
                ?.replace("payerName","CTRNCLIENT_NAME")
                ?.replace("sum","MTRNSUM")
                ?.replace("payeeINN","CTRNMFOA")
                ?.replace("payerINN","CTRNMFOO")

    }

    fun affirmTransaction(itrnnum: Long): TrnAffirmResponse {
        val connection = jdbcTemplate?.getDataSource()?.getConnection();
        val callableStatement: CallableStatement? = connection?.prepareCall("{call ? := ${CALL_AFFIRM}") ?: null
        callableStatement?.setLong(2, itrnnum)
        callableStatement?.registerOutParameter(1, java.sql.Types.VARCHAR)
        callableStatement?.executeUpdate()
        val result = callableStatement?.getString(1)
        connection?.close()
        if (result != null && result.startsWith("SUCCESS")) {
            return TrnAffirmResponse( "SUCCESS",result,itrnnum)
        } else return TrnAffirmResponse("ERROR",result,itrnnum)

    }
}



