package ru.inversion.rtgs.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.inversion.rtgs.payload.reponse.BalanceResponse
import java.sql.CallableStatement
import java.util.*

/**
 * @author Dmitry Hvastunov
 * @created 01.06.2021
 * @project rtgs
 */

@Service
class BalanceService {

    val CALL_BALANCE = "rtgs_package.get_all_balance_position(?,?,?,?,?,?)"


    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null


    fun getBalance(date: String, cur: String, acc: String): BalanceResponse {

        val connection = jdbcTemplate?.getDataSource()?.getConnection();
        val callableStatement: CallableStatement? = connection?.prepareCall("{call ${CALL_BALANCE}") ?: null
        callableStatement?.setString(1, date)
        callableStatement?.setString(2, cur)
        callableStatement?.setString(3, acc)

        callableStatement?.registerOutParameter(4, java.sql.Types.VARCHAR)
        callableStatement?.registerOutParameter(5, java.sql.Types.VARCHAR)
        callableStatement?.registerOutParameter(6, java.sql.Types.VARCHAR)

        callableStatement?.executeUpdate()

        var balResp = BalanceResponse(
                callableStatement?.getString(4),
                callableStatement?.getString(5),
                callableStatement?.getString(6)
        )

        return balResp

    }
}