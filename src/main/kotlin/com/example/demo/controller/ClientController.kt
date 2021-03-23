package com.example.demo.controller

import com.example.demo.model.Client
import com.example.demo.utilities.ClientDAO
import com.example.demo.utilities.ClientEntity
import com.example.demo.utilities.Utils.toEntity
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.SortedFilteredList
import tornadofx.observable

class ClientController: Controller() {

    private val clientDao = ClientDAO

    fun update(client: Client) {
        clientDao.update(client.toEntity())
    }

    fun save(client: Client) : ClientEntity {
        return clientDao.save(client.toEntity())
    }

    fun delete(person: Client) {
        clientDao.delete(person.toEntity())
    }

    fun clients() = clientDao.readAll().map { entity ->
        Client(entity.id, entity.telefon, entity.nom)
    }.observable()

    fun closeConnection() = ClientDAO.closeConnection()

    /*val clients = SortedFilteredList(items = getAllClients().observable())

    fun save (client: Client?) {
        println("Client modificat ${client?.telefon} / ${client?.nom}")
    }

    fun putClient(oldClient : Client, newTelefon: String, newNom: String) {
        val newClient = Client(newTelefon, newNom)
        val dao = ClientDAO()
        dao.updateClient(oldClient.telefon, newClient)
        with(clients) {
            remove(oldClient)
            add(newClient)
        }
    }

    fun create (client: Client?) {
        println("Client creat ${client?.telefon} / ${client?.nom}")
    }

    fun postClient(telefon: String, nom: String) {
        val client = Client(telefon, nom)
        val dao = ClientDAO()
        dao.addClient(client)
        clients.add(client)

    }

    fun delete (client: Client?) {
        println("Client eliminat ${client?.telefon} / ${client?.nom}")
    }

    fun deleteClients(client: Client) {
        val dao = ClientDAO()
        dao.deleteClient(client.telefon)
        clients.remove(client)
    }

    fun getAllClients(): ObservableList<Client> = ClientDAO().readClient().observable()*/
}