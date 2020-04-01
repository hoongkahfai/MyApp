package com.example.myapp.Model

class Users {
    var name: String? = null
    var phone: String? = null
    var password: String? = null

    constructor() {}
    constructor(name: String?, phone: String?, password: String?) {
        this.name = name
        this.phone = phone
        this.password = password
    }

}