package edu.ktu.firstapp.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("authEmailError")
fun TextInputLayout.setAuthEmailError(authError: AuthError)
{
    error = when(authError){
        AuthError.NoEmailProvided -> "No email provided"
        AuthError.MalformedEmail -> "Email is invalid"
        AuthError.UserExists -> "User with such email already exists"
        else -> null
    }
}

@BindingAdapter("authPasswordError")
fun TextInputLayout.setAuthPasswordError(authError: AuthError)
{
    error = when(authError){
        AuthError.NoPasswordProvided-> "No password provided"
        AuthError.WrongCredentials-> "Email or password is incorrect"
        AuthError.PasswordsDoNotMatch-> "Password does not match"
        AuthError.IncorrectPassword-> "Password is incorrect"
        AuthError.EmptyField-> "Field is empty"
        else->null
    }
}