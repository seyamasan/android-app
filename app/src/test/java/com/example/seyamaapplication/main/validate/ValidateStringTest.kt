package com.example.seyamaapplication.main.validate

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe


class ValidateStringTest : FunSpec() {
    init {
        context("正常系") {
            context("ひらがな") {
                val validateString = ValidateString()
                validateString.validateSymbol("あ") shouldBe true
            }
            context("カタカナ") {
                val validateString = ValidateString()
                validateString.validateSymbol("カ") shouldBe false
            }
            context("漢字") {
                val validateString = ValidateString()
                validateString.validateSymbol("差") shouldBe false
            }
            context("ローマ字") {
                val validateString = ValidateString()
                validateString.validateSymbol("t") shouldBe false
            }
            context("数字") {
                val validateString = ValidateString()
                validateString.validateSymbol("1") shouldBe false
            }
        }
        context("異常系") {
            context("記号のみ") {
                val validateString = ValidateString()
                validateString.validateSymbol("#") shouldBe false
            }
            context("ひらがなと記号") {
                val validateString = ValidateString()
                validateString.validateSymbol("あ#") shouldBe true
            }
            context("カタカナと記号") {
                val validateString = ValidateString()
                validateString.validateSymbol("カ#") shouldBe true
            }
            context("漢字と記号") {
                val validateString = ValidateString()
                validateString.validateSymbol("差#") shouldBe true
            }
            context("ローマ字と記号") {
                val validateString = ValidateString()
                validateString.validateSymbol("t#") shouldBe true
            }
            context("数字と記号") {
                val validateString = ValidateString()
                validateString.validateSymbol("#1") shouldBe true
            }
        }
    }
}