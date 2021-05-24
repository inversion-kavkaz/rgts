package ru.inversion.rtgs.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class RtgsTrn(
        @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       val id: Long?,
        /**Номер ЭС в течение опердня.*/
       @Column(nullable = false)
       val edNo: Long?,
        /**Уникальный идентификатор составителя ЭС */
       @Column(nullable = false,updatable = false)
       val edAuthor: String?,
        /**Уникальный идентификатор получателя ЭС */
       @Column(nullable = false)
       val edReceiver: String?,
        /**Вид операции*/
       val transKind: String?,
        /**Очередность платежа*/
       val priority: Long? = 1,

        /**Сумма платежа*/
       @Column(nullable = false)
       val sum: Double? = null,

        /**Номер счета плательщика*/
       @Column(nullable = false)
       val payerPersonalAcc: String?,
        /**ИНН плательщика*/
       @Column(nullable = false)
       val payerINN: String?,
        /**Номер счета банка плательщика */
       @Column(nullable = false)
       val payerCorrespAcc: String?,

        /**Номер счета получателя*/
       @Column(nullable = false)
       val payeePersonalAcc: String?,
        /**ИНН получателя*/
       @Column(nullable = false)
       val payeeINN: String?,
        /**Номер счета банка получателя */
       @Column(nullable = false)
       val payeeCorrespAcc: String?,
        /**Id отправителя из таблицы сотрудников банка */
        @Column(nullable = false,updatable = false)
       val userId: Long?,
        /**Назначение платежа */
       val purpose: String?




){

       constructor(): this(
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

       /**Дата составления ЭС*/
       @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
       @Column(updatable = false)
       var edDate: LocalDateTime? = null

       @PrePersist
    fun onCreate(){
        this.edDate = LocalDateTime.now()
    }

}
