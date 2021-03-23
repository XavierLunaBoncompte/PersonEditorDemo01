package com.example.demo.utilities


import com.example.demo.model.Client
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.util.Duration
//import org.controlsfx.control.Notifications
//import org.controlsfx.control.action.Action
//import org.eclipse.fx.core.Tuple

import javafx.scene.control.TextInputDialog
import java.util.*


object Utils {

    internal fun Client.toEntity(): ClientEntity = ClientEntity(id, telefon, nom)

    fun getName(title: String, header: String): String? {
        var returnedName: String? = null
        val td = TextInputDialog("Nothing")
        td.title = title
        td.headerText = header
        val result: Optional<String> = td.showAndWait()
        if (result.isPresent) {
            returnedName = td.editor.text
        }
        return returnedName
    }

}