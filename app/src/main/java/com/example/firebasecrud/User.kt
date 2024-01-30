package com.example.firebasecrud

data class User(
    var userKey: String,
    var userName: String,
    var userAge: String
) {
    // 기본생성자를 만들어줌.
    constructor(): this("","","")
}
