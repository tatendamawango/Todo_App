package edu.ktu.firstapp.utils

enum class AuthError {
    NoError,
    NoEmailProvided,
    MalformedEmail,
    NoPasswordProvided,
    WrongCredentials,
    UserExists,
    PasswordsDoNotMatch,
    IncorrectPassword,
    EmptyField
}