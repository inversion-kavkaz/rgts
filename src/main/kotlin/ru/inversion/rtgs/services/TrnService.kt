package ru.inversion.rtgs.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.inversion.rtgs.entity.RtgsTrn
import ru.inversion.rtgs.facade.TrnFacade
import ru.inversion.rtgs.payload.reponse.TrnCreateResponse
import ru.inversion.rtgs.payload.request.TrnRequest
import ru.inversion.rtgs.repository.TrnRepository
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
        if(trn.edNo == null) trn.edNo = -1
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

        if (result != null && result.startsWith("SUCCESS")){
            return TrnCreateResponse(trnRepository.getLastTransaction(trnnum, trnanum), "Transaction is registered")
        }
        else return TrnCreateResponse(null, result)
    }

    fun update(trn : RtgsTrn) : TrnCreateResponse {
        val resString: String?
        if(trn.itrnnum != null) {
            resString = delete(trn.itrnnum)
            if(!resString.isNullOrEmpty() && resString.startsWith("SUCCESS"))
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


}
