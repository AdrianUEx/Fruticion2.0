package com.example.fruticion.util

class CredentialCheck private constructor() {

    var fail: Boolean = false
    var msg: String = ""
    var error: CredentialError = CredentialError.Init

    companion object {

        private const val longMin = 3

        private val check = arrayOf(
            CredentialCheck().apply {
                fail = false
                msg = "Credentials OK"
                error = CredentialError.Success
            },
            CredentialCheck().apply {
                fail = true
                msg = "Invalid Username"
                error = CredentialError.UsernameError
            },
            CredentialCheck().apply {
                fail = true
                msg = "Invalid password"
                error = CredentialError.PasswordError
            },
            CredentialCheck().apply {
                fail = true
                msg = "Passwords don't match"
                error = CredentialError.PasswordError
            }


        )

        fun login(username: String, password: String): CredentialCheck {
            return if(username.isBlank() || username.length < longMin)
                check[1]
            else if(password.isBlank() || password.length < longMin)
                check[2]
            else
                check[0]
        }

        fun join(username: String, password: String, secondpass: String): CredentialCheck {

            return if (username.isBlank() || username.length < longMin)
                check[1]
            else if (password.isBlank() || password.length < longMin)
                check[2]
            else if (password != secondpass)
                check[3]
            else
                check[0]
        }

        fun passwordOk(password: String, secondpass: String): CredentialCheck {

            return if(password!=secondpass)
                check[3]
            else
                check[0]
        }
    }

    enum class CredentialError{
        Init, PasswordError, UsernameError, Success
    }
}