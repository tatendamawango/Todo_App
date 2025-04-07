# ✅ ToDo Android App (Firebase + MVVM)

A sleek, Firebase-powered ToDo app built using **MVVM architecture** with Material Design UI, supporting user authentication, task management, and live synchronization with Firebase Realtime Database.

---

## 📱 Features

- 🔐 Firebase **Authentication** (Sign up, Login, Profile editing)
- 📌 **Create**, **edit**, and **delete** tasks
- 📊 Categorize tasks: `In Progress`, `Finished`, `ToDo`
- 📅 Display tasks with **dates** in RecyclerViews
- 📡 Sync tasks to **Firebase Realtime Database**
- 🔄 Two-way data binding with **ViewModel** & **LiveData**
- 🎨 Intuitive and responsive Material Design interface
- 🚫 Custom **authentication error handling**

---

## 📁 Project Structure
```
├── adapters/ # RecyclerView adapters for task lists │ ├── FinishedAdapter.kt │ ├── InProgressAdapter.kt │ └── TaskAdapter.kt

├── fragments/ # Screens for tasks and user flows │ ├── LoginFragment.kt │ ├── SignupFragment.kt │ ├── TasksFragment.kt │ ├── InProgressFragment.kt │ ├── FinishedFragment.kt │ └── EditTaskFragment.kt │ ...

├── models/ # Data classes │ └── Task.kt

├── utils/ # Helper classes & error binding │ ├── AuthError.kt │ ├── BindingAdapters.kt │ └── Regex.kt

├── viewModels/ # MVVM ViewModels │ ├── LoginViewModel.kt │ ├── SignUpViewModel.kt │ ├── EditTaskViewModel.kt │ └── EditProfileViewModel.kt

└── MainActivity.kt # NavHost for fragment transactions
```
---

## 🔄 Flow Overview
IntroFragment → Login / Signup → TasksFragment ↓ ↓ ↓ EditProfile NewTask EditTask ↓ Categorized (Todo / InProgress / Finished)


---

## ☁️ Firebase Integration

- **FirebaseAuth** for login, signup, password changes
- **Firebase Realtime Database** for storing and updating tasks
- Task data is scoped per user (`Tasks/{userId}/taskId`)
- Realtime syncing of edits and deletions with RecyclerView adapters

---

## 💡 Architecture

This app uses **MVVM**:
- `ViewModel` manages logic and state
- `LiveData` exposes data changes
- `Data Binding` connects UI ↔ ViewModel
- `Adapters` handle RecyclerView item logic

---

## 🧪 Sample: Editing Password Logic

```kotlin
val credential = EmailAuthProvider.getCredential(currentEmail, oldPassword)
FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)?.addOnCompleteListener {
    if (it.isSuccessful) {
        updatePassword()
    } else {
        _editProfileError.value = AuthError.IncorrectPassword
    }
}
```
## 🚧 Future Enhancements
- 🔔 Notifications for due tasks

- 🌐 Firestore support for improved scalability

- 🌓 Dark mode support

- 📂 Task categorization with custom labels
