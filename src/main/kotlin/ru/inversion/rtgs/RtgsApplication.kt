package ru.inversion.rtgs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RtgsApplication

fun main(args: Array<String>) {
    runApplication<RtgsApplication>(*args)
}
