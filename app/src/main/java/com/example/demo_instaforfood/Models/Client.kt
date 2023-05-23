package com.example.demo_instaforfood.Models

data class Client(
    val address: Any,
    val client_phones: List<ClientPhone>,
    val email: Any,
    val family_members: List<Any>,
    val first_name: String,
    val id: Int,
    val last_name: Any,
    val middle_name: Any,
    val name: String,
    val nickname: Any,
    val quick_note: Any
) {
    data class ClientPhone(
        val id: Int,
        val number: String,
        val primary: Boolean
    )
}