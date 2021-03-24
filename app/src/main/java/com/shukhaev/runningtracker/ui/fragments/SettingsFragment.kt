package com.shukhaev.runningtracker.ui.fragments

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.google.android.material.snackbar.Snackbar
import com.shukhaev.runningtracker.R
import com.shukhaev.runningtracker.utils.Util.KEY_NAME
import com.shukhaev.runningtracker.utils.Util.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_statistics.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPref()

        btnApplyChanges.setOnClickListener {
            val isSuccess = applyChangesToSharedPref()
            if (isSuccess) {
                Snackbar.make(view, "Changes saved", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "Please enter all fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadFieldsFromSharedPref() {
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)

        etName.setText(name)
        etWeight.setText(weight.toString())
    }

    private fun applyChangesToSharedPref(): Boolean {
        val name = etName.text.toString()
        val weight = etWeight.text.toString()
        return if (name.isBlank() || weight.isBlank()) {
            false
        } else {
            sharedPreferences.edit()
                .putString(KEY_NAME, name)
                .putFloat(KEY_WEIGHT, weight.toFloat())
                .apply()

            val toolbarText = "Let's go $name!"
            requireActivity().tvToolbarTitle.text = toolbarText
            true
        }
    }

}