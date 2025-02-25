package com.shukhaev.runningtracker.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shukhaev.runningtracker.R
import kotlinx.android.synthetic.main.item_run.view.*
import java.util.*

class TrackingCancelDialog : DialogFragment() {

    private var yesListener: (() -> Unit)? = null

    fun setYesListener(listener:()->Unit){
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel Run?")
            .setMessage("Are u sure to cancel tracking and delete all data?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes") { _, _ ->
                yesListener?.let {yes->
                    yes()
                }
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}