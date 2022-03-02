package android.example.rpgenerator

import java.security.SecureRandom

class MakePassword {
        val letters : String = "abcdefghijklmnopqrstuvwxyz"
        val uppercase : String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers : String = "0123456789"
        val symbols : String = "@#=+!£$%&?"

    fun generatePassword(isWithLetters: Boolean,
                         isWithUppercase: Boolean,
                         isWithNumbers: Boolean,
                         isWithSpecial: Boolean,
                         length: Int) : String {
        var i : Int = 0
        var result : String = ""

        if(isWithLetters){ result += this.letters }
        if(isWithUppercase){ result += this.uppercase }
        if(isWithNumbers){ result += this.numbers }
        if(isWithSpecial){ result += this.symbols }

        val rnd = SecureRandom.getInstance("SHA1PRNG")
        val sbPassword = StringBuilder(length)

        while (i < length){
            val randomInt : Int = rnd.nextInt(result.length)
            sbPassword.append(result[randomInt])
            i++
        }

        return sbPassword.toString()
    }
}