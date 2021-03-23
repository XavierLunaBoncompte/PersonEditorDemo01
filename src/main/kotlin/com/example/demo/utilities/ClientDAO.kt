package com.example.demo.utilities


import com.example.demo.model.Client
import com.example.demo.utilities.ClientDAO.connection
import java.sql.ResultSet
import java.sql.Timestamp

object ClientDAO {

    private val connection = Database.connection

    private fun getLast(): ClientEntity {
        var last: ClientEntity? = null
        val resultSet = connection.createStatement().executeQuery("SELECT * FROM client c ORDER BY id DESC")
        if (resultSet.next()) last = ClientEntity(
                resultSet.getInt("id"),
                resultSet.getString("telefon"),
                resultSet.getString("nom"))
        return last!!
    }

    fun save (client: ClientEntity): ClientEntity {
        val preparedStatement = connection.prepareStatement("INSERT INTO client(telefon, nom, date_deleted) VALUES (?, ?, ?)")
        preparedStatement.setString(1, client.telefon)
        preparedStatement.setString(2, client.nom)
        preparedStatement.setLong(3, 0)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        return getLast()
    }

    fun readAll(): List<ClientEntity> {
        val resultSet = connection.createStatement().executeQuery("SELECT * FROM client c WHERE c.date_deleted = 0  ORDER BY telefon")
        val clientList = ArrayList<ClientEntity>()
        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val telefon = resultSet.getString("telefon")
            val nom = resultSet.getString("nom")
            clientList += ClientEntity(id, telefon, nom)
        }
        resultSet.close()
        return clientList
    }

    fun update(client: ClientEntity) {
        val preparedStatement = connection.prepareStatement("UPDATE client c set c.telefon = ?, c.nom = ? WHERE c.id = ?")
        preparedStatement.setString(1, client.telefon)
        preparedStatement.setString(2, client.nom)
        preparedStatement.setInt(3, client.id)
        preparedStatement.executeUpdate()
        preparedStatement.close()
    }

    fun delete(client: ClientEntity) {
        val dateDeleted = System.currentTimeMillis()
        val preparedStatement = connection.prepareStatement("UPDATE client SET date_deleted = ? WHERE id = ?")
        preparedStatement.setLong(1, dateDeleted)
        preparedStatement.setInt(2, client.id)
        preparedStatement.executeUpdate()
        preparedStatement.close()
    }

    fun closeConnection() = Database.closeConnection()

    /*fun addClient (client: Client) {
        val connection = Database().connection
        val preparedStatement = connection.prepareStatement("INSERT INTO client(telefon, nom) VALUES (?, ?)")
        preparedStatement.setString(1, client.telefon)
        preparedStatement.setString(2, client.nom)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }

    fun readClient(): List<Client> {
        val connection = Database().connection
        val resultSet = connection.createStatement().executeQuery("SELECT * FROM client")
        val clientList = ArrayList<Client>()
        while (resultSet.next()) {
            val telefon = resultSet.getString("telefon")
            val nom = resultSet.getString("nom")
            clientList += Client(telefon, nom)
        }
        resultSet.close()
        connection.close()
        return clientList
    }

    fun updateClient(telefon : String, client : Client): Client {
        val connection = Database().connection
        var param = ""
        val paramNom = ", nom = ? "
        var optinalParamIndexNom = 2
        if (client.nom.isNotEmpty()) param = paramNom
        val preparedStatement = connection.prepareStatement("UPDATE client set telefon = ? $param WHERE telefon = ?")
        preparedStatement.setString(1, client.telefon)
        if (param.isNotEmpty()) {
            preparedStatement.setString(optinalParamIndexNom, client.nom)
            optinalParamIndexNom = optinalParamIndexNom.inc()
        }
        preparedStatement.setString(optinalParamIndexNom, telefon)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
        return client
    }

    fun deleteClient(telefon: String) {
        val timestamp = Timestamp(System.currentTimeMillis())
        val connection = Database().connection
        val preparedStatement = connection.prepareStatement("DELETE FROM client WHERE telefon = ?")
        //preparedStatement.setString(1, timestamp.toString())
        preparedStatement.setString(1, telefon)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }*/
}