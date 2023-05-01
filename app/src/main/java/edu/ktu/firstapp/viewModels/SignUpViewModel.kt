package edu.ktu.firstapp.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.*
import edu.ktu.firstapp.utils.AuthError

class SignUpViewModel: ViewModel() {
    private val _signUpError = MutableLiveData(AuthError.NoError)
    val signUpError: LiveData<AuthError> = _signUpError

    private val _currentUser = MutableLiveData<FirebaseUser>(null)
    val currentUser: LiveData<FirebaseUser> = _currentUser

    fun signUp(email: String, password: String, password2: String) {
        if (email.isNullOrEmpty()) {
            _signUpError.value = AuthError.NoEmailProvided
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _signUpError.value = AuthError.MalformedEmail
            return
        }
        if (password.isNullOrEmpty()) {
            _signUpError.value = AuthError.NoPasswordProvided
            return
        }

        if (password2.isNullOrEmpty()) {
            _signUpError.value = AuthError.NoPasswordProvided
            return
        }

        if (password != password2) {
            _signUpError.value = AuthError.PasswordsDoNotMatch
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signUpError.value = AuthError.NoError
                    _currentUser.value = task.result.user
                } else {
                    task.exception?.let {exception ->
                        _signUpError.value = when (exception) {
                            is FirebaseAuthInvalidCredentialsException ->
                                AuthError.MalformedEmail
                            is FirebaseAuthUserCollisionException ->
                                AuthError.UserExists
                            else -> AuthError.NoError
                        }
                    }
                }
            }
    }

}