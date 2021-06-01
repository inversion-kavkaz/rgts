package ru.inversion.rtgs.services.utils

import org.springframework.jdbc.core.RowMapper
import ru.inversion.rtgs.entity.RtgsTrn
import java.sql.ResultSet
import java.sql.SQLException
import kotlin.jvm.Throws

/**
 * @author Dmitry Hvastunov
 * @created 01.06.2021
 * @project rtgs
 */

class CustomerRowMapper : RowMapper<RtgsTrn?> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): RtgsTrn {
        val trn = RtgsTrn(
                rs.getLong("itrnnum"),
                rs.getLong("itrnanum"),
                rs.getLong("ITRNDOCNUM"),
                rs.getString("CTRNMFOO"),
                rs.getString("CTRNMFOA"),
                rs.getLong("ITRNTYPE"),
                rs.getLong("ITRNPRIORITY"),
                rs.getLong("MTRNSUM"),
                rs.getString("CTRNCLIENT_ACC"),
                rs.getString("CTRNMFOO"),
                rs.getString("CTRNACCD"),
                rs.getString("CTRNACCA"),
                rs.getString("CTRNMFOA"),
                rs.getString("CTRNACCC"),
                rs.getString("CTRNIDOPEN"),
                rs.getString("CTRNPURP"),
                rs.getString("CTRNCLIENT_NAME"),
                rs.getString("CTRNOWNA"),
                rs.getDate("DTRNDOC"),
                rs.getString("CTRNSTATE1"),
                rs.getString("CTRNCUR")
        )
        return trn
    }
}
