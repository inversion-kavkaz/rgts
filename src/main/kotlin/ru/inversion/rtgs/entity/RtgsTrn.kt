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
       @Column(nullable = false,updatable = false)
       val EDNo: Long?,
        /**Уникальный идентификатор составителя ЭС */
       @Column(nullable = false,updatable = false)
       val EDAuthor: String?,
        /**Уникальный идентификатор получателя ЭС */
       @Column(nullable = false,updatable = false)
       val EDReceiver: String?,
        /**Вид операции*/
       val TransKind: String?,
        /**Очередность платежа*/
       val Priority: Long? = 1,

        /**Сумма платежа*/
       @Column(nullable = false,updatable = false)
       val Sum: Long? = 0,

        /**Номер счета плательщика*/
       @Column(nullable = false,updatable = false)
       val PayerPersonalAcc: String?,
        /**ИНН плательщика*/
       @Column(nullable = false,updatable = false)
       val PayerINN: String?,
        /**Номер счета банка плательщика */
       @Column(nullable = false,updatable = false)
       val PayerCorrespAcc: String?,

        /**Номер счета получателя*/
       @Column(nullable = false,updatable = false)
       val PayeePersonalAcc: String?,
        /**ИНН получателя*/
       @Column(nullable = false,updatable = false)
       val PayeeINN: String?,
        /**Номер счета банка получателя */
       @Column(nullable = false,updatable = false)
       val PayeeCorrespAcc: String?,
        /**Id отправителя из таблицы сотрудников банка */
        @Column(nullable = false,updatable = false)
       val userId: Long?


){

       constructor(): this(null,null,null,null,null,null,null,null,null,null,null, null,null,null)

       /**Дата составления ЭС*/
       @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
       @Column(nullable = false,updatable = false)
       var EDDate: LocalDateTime? = null

       @PrePersist
    fun onCreate(){
        this.EDDate = LocalDateTime.now()
    }

}
