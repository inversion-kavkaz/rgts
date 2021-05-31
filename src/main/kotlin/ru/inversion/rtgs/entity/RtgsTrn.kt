package ru.inversion.rtgs.entity

import lombok.Data
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.*
import kotlin.jvm.Transient

@Data
@Entity
@Table(name = "trn", schema = "xxi")

data class RtgsTrn(
        //@GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id @Column(name = "itrnnum") val itrnnum: Long?,
        @Column(name = "itrnanum") val itrnanum: Long? = null,
        /**Номер ЭС в течение опердня.*/
        @Column(name = "ITRNDOCNUM") var edNo: Long?,
        /**Уникальный идентификатор составителя ЭС */
        @Column(name = "CTRNMFOO") val edAuthor: String?,
        /**Уникальный идентификатор получателя ЭС */
        @Column(name = "CTRNMFOA", insertable = false, updatable = false)
        val edReceiver: String?,
        /**Вид операции*/
        @Column(name = "ITRNTYPE") val transKind: Long?,
        /**Очередность платежа*/
        @Column(name = "ITRNPRIORITY") val priority: Long? = 1,
        /**Сумма платежа*/
        @Column(name = "MTRNSUM") val sum: Long? = null,
        /**Номер счета плательщика*/
        @Column(name = "CTRNCLIENT_ACC") val payerPersonalAcc: String?,
        /**ИНН плательщика*/
        @Column(name = "CTRNMFOO", insertable = false, updatable = false)
        val payerINN: String?,
        /**Номер счета банка плательщика */
        @Column(name = "CTRNACCD") val payerCorrespAcc: String?,
        /**Номер счета получателя*/
        @Column(name = "CTRNACCA") val payeePersonalAcc: String?,
        /**ИНН получателя*/
        @Column(name = "CTRNMFOA") val payeeINN: String?,
        /**Номер счета банка получателя */
        @Column(name = "CTRNACCC") val payeeCorrespAcc: String?,
        /**Id отправителя из таблицы сотрудников банка */
        @Column(name = "CTRNIDOPEN") val login: String?,
        /**Назначение платежа */
        @Column(name = "CTRNPURP") val purpose: String?,
        /**наименование плательшика*/
        @Column(name = "CTRNCLIENT_NAME") val payerName: String? = null,
        /**наименование получателя*/
        @Column(name = "CTRNOWNA") val payeeName: String? = null,
        /**Дата составления ЭС*/
        @Column(name = "DTRNDOC")
        //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", shape = JsonFormat.Shape.STRING)
        var edDate: Date? = null,

        /**Статус документа*/
        @Column(name = "CTRNSTATE1") val status: String? = null,
        /**Валюта платежа*/
        @Column(name = "CTRNCUR") val currency: String? = "",
        /**Результат выполнения транзакции*/
        @Transient
        val result: String? = null
) {

    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )


    @PrePersist
    fun onCreate() {
        this.edDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) as Date?
    }

}
