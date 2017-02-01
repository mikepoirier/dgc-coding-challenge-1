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

        val isCardValid = isValidCard(card)

        var status = 200

        if(!isCardValid) {
            status = 400
        }

        return ResponseEntity.status(status).body(CardResponse(card, isValidCard(card)))
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

//class Book() {
//    lateinit var ISBN: String
//    lateinit var title: String
//    lateinit var author: String
//    var coverURL: String? = null
//
//    constructor(
//            ISBN: String,
//            title: String,
//            author: String,
//            coverURL: String? = null): this() {
//        this.ISBN = ISBN
//        this.title = title
//        this.author = @author
//        this.coverURL = coverURL
//    }
//}

class CardRequest {
    lateinit var cardNumber: String
}

data class CardResponse(val cardNumber: String, val isValid: Boolean)
