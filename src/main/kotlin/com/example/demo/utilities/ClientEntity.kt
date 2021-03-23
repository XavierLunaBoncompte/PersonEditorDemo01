package com.example.demo.utilities

import com.example.demo.model.Client

data class ClientEntity(val id: Int = 0, val telefon: String, val nom: String)

fun ClientEntity.toClient() = Client(id, telefon, nom)