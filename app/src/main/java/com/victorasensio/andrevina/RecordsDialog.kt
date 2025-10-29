package com.victorasensio.andrevina

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class RecordsDialog : DialogFragment() {

    interface RecordsDialogListener {
        fun onRecordAccepted(name: String, attempts: Int)
    }

    private var listener: RecordsDialogListener? = null
    private var attempts: Int = 0

    companion object {
        private const val ARG_ATTEMPTS = "attempts"

        fun newInstance(attempts: Int): RecordsDialog {
            val fragment = RecordsDialog()
            val args = Bundle()
            args.putInt(ARG_ATTEMPTS, attempts)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            attempts = it.getInt(ARG_ATTEMPTS, 0)
        }
    }

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

        val editTextName = view.findViewById<EditText>(R.id.username)

        builder.setView(view)
            .setTitle("Guardar Record")
            .setMessage("Has encertat en $attempts intents! Vols guardar el teu record?")
            .setPositiveButton("Acceptar") { dialog, _ ->
                val name = editTextName.text.toString().trim()
                if (name.isNotEmpty()) {
                    listener?.onRecordAccepted(name, attempts)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}