package com.example.demo.view

import com.example.demo.controller.ClientController
import com.example.demo.model.Client
import com.example.demo.utilities.toClient
import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.input.KeyCombination
import javafx.scene.layout.BorderPane
import tornadofx.*
import tornadofx.controlsfx.infoNotification

class TableClientEditor : View("CLIENT EDITOR") {
    override val root: BorderPane = BorderPane()
    private val clients: ObservableList<Client>
    private var model: TableViewEditModel<Client> by singleAssign()
    private val controller: ClientController by inject()

    init {
        clients = controller.clients()
        with(root) {
            top {
                menubar {
                    menu("File") {
//                        menu("New") {
//                            item("Facebook")
//                            item("Twitter")
//                        }
//                        separator()
                        item("Add", KeyCombination.valueOf("Shortcut+A")).action {
//                            model.commit()
                            save(Client(telefon = "New", nom = "New"))
                        }
                        item("Quit", KeyCombination.valueOf("Shortcut+Q")).action {
                            Platform.exit()
                        }
                    }
                    menu("Edit") {
                        item("Copy")
                        item("Paste")
                    }
                }
            }
            center {
                tableview(clients) {
                    column("Telefon", Client::telefonProperty).makeEditable()
                    column("Nom", Client::nomProperty).makeEditable()
                    contextmenu {
                        item("Remove").action {
                            selectedItem?.apply { delete(this) }
                        }
                        item("Update").action {
                            selectedItem?.apply {
                                model.commit()
                                update(this)
                            }
                        }
                        item("Change Status").action {
                            selectedItem?.apply { println("Changing Status for $telefon") }
                        }
                    }
                    isEditable = true
                    enableCellEditing()
                    enableDirtyTracking()
                    model = editModel


                }
            }
            bottom {
                buttonbar {
                    button("COMMIT").setOnAction {
                        model.commit()
                        model.items.forEach {
                            if (it.value.isDirty) {}
                        }
                    }
                    button("ROLLBACK").setOnAction {
                        model.rollback()
                    }
                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        currentWindow?.setOnCloseRequest { controller.closeConnection() }
    }

    private fun update(client: Client) {
        if (client.id > 0) {
            controller.update(client)
            infoNotification(
                messages["client_editor"],
                messages["el_registro_se_ha_actualizado_correctamente"],
                Pos.CENTER
            )
        } else {
            // save new record???
            infoNotification(messages["client_editor"], messages["no_hay_nada_que_actualizar"], Pos.CENTER)
        }
    }

    private fun save(client: Client) {
        val record = controller.save(client)
        clients.add(record.toClient())
//        clients.sortBy { it.name }
        infoNotification(
            messages["client_editor"],
            messages["el_registro_se_ha_guardado_satisfactoriamente"],
            Pos.CENTER
        )
    }

    private fun delete(client: Client): Unit {
        if (client.id > 0) {
            messages
            confirm(title = messages["client_editor"], header = messages["estas_seguro"]) {
                controller.delete(client)
                clients.remove(client)
                information(
                    title = messages["client_editor"],
                    header = messages["el_registro_se_ha_eliminado_correctamente"],
                    content = """${client.telefon} ${messages["se_ha_marcado_como_borrado"]}"""
                )
            }
        } else {
            infoNotification(
                messages["client_editor"],
                messages["el_registro_no_esta_presente_en_la_tabla"],
                Pos.CENTER
            )
        }
    }


}