package com.victorasensio.andrevina

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.content.Context

class RecordsDialog : DialogFragment() {

    interface RecordsDialogListenes {
        fun onRecordAccepted(name: String, attempts: Int)
    }

    private var listener: RecordsDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as RecordsDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement RecordsDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog, null)

        // Asumiendo que en el layout 'dialog' tienes un EditText para nombre con id editTextName y otro para intentos con id editTextAttempts
        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val editTextAttempts = view.findViewById<EditText>(R.id.editTextAttempts)

        builder.setView(view)
            .setTitle("Guardar Record")
            .setMessage("Vols guardar el teu record?")
            .setPositiveButton("Accept") { dialog, _ ->
                val name = editTextName.text.toString()
                val attempts = editTextAttempts.text.toString().toIntOrNull() ?: 0
                listener?.onRecordAccepted(name, attempts)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        return builder.create()
    }
}
