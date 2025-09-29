package com.victorasensio.andrevina

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class RecordsDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog, null))
                .setTitle("Guardar Record")
                .setMessage("Vols guardar el teu record?")
                .setPositiveButton("Accept") { dialog, id ->
                    // Aquí va la lógica para iniciar sesión
                }
                .setNegativeButton( "Cancel") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
