package edu.ktu.firstapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.ktu.firstapp.utils.AuthError

class EditProfileViewModel: ViewModel() {
    private val _editProfileError = MutableLiveData(AuthError.NoError)
    val editProfileError: LiveData<AuthError> = _editProfileError

    private val _currentUser = MutableLiveData<FirebaseUser>(null)
    val currentUser: LiveData<FirebaseUser> = _currentUser

    fun editProfile(password: String, password2: String, password3: String) {
        if (password.isNullOrEmpty()) {
            _editProfileError.value = AuthError.NoPasswordProvided
            return
        }

        if (password2.isNullOrEmpty()) {
            _editProfileError.value = AuthError.NoPasswordProvided
            return
        }

        if (password3.isNullOrEmpty()) {
            _editProfileError.value = AuthError.NoPasswordProvided
            return
        }

        if (password2 != password3) {
            _editProfileError.value = AuthError.PasswordsDoNotMatch
            return
        }

        val credential = EmailAuthProvider.getCredential(FirebaseAuth.getInstance().currentUser?.email.toString(), password)

        FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)?.addOnCompleteListener {
            if (it.isSuccessful) {
                FirebaseAuth.getInstance().currentUser?.updatePassword(password2)?.addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        _editProfileError.value = AuthError.NoError
                    } else {
                        _editProfileError.value = AuthError.IncorrectPassword
                    }
                }
            } else {
                _editProfileError.value = AuthError.IncorrectPassword
            }
        }


    }
}