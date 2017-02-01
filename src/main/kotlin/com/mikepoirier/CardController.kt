package com.mikepoirier

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CardController {

    @PutMapping("/card")
    fun verifyCard(@RequestBody cardRequest: CardRequest): ResponseEntity<CardResponse> {
        val card = cardRequest.cardNumber
        return ResponseEntity.ok(CardResponse(card, isValidCard(card)))
    }

    fun sumDigits(number: Int): Int {
        if(number > 9) {
            return sumDigits(number.toString().toCharArray().map { it.toString().toInt() }.sum())
        } else {
            return number
        }
    }

    fun isValidCard(cardNumber: String): Boolean {
        val cardNumberInts = cardNumber.toCharArray().map { it.toString().toInt() }

        val checksum = cardNumberInts.mapIndexed { index, value ->
            var sum = 0
            if (index % 2 == 0) {
                sum += sumDigits(value * 2)
            } else {
                sum += value
            }
            sum
        }.sum()

        return checksum % 10 == 0
    }
}

data class CardRequest(val cardNumber: String)

data class CardResponse(val cardNumber: String, val isValid: Boolean)
