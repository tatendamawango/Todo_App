package edu.ktu.firstapp.utils

import java.util.regex.Pattern

val PASSWORD: Pattern
    get() = Pattern.compile(
        "(?=(.*[A-Z])+)" +
                "(?=(.*[A-Z])+)" +
                "(?=(.*[!@#\$%^&*()\\-__+.\\d])+)" +
                ".{8,}"
    )