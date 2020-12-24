package com.sherryyuan.emomtimer.settings

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sherryyuan.emomtimer.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        setupAboutEmomPreference()
        setupAboutTabataPreference()
        setupSavedExercisesPreference()
    }

    private fun setupAboutEmomPreference() {
        findPreference<Preference>("about_emom")?.setOnPreferenceClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsToAboutWorkout(
                    R.string.what_is_emom,
                    R.string.emom_explanation
                )
            )
            true
        }
    }

    private fun setupAboutTabataPreference() {
        findPreference<Preference>("about_tabata")?.setOnPreferenceClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsToAboutWorkout(
                    R.string.what_is_tabata,
                    R.string.tabata_explanation
                )
            )
            true
        }
    }

    private fun setupSavedExercisesPreference() {
        findPreference<Preference>("saved_exercises")?.setOnPreferenceClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToExerciseNames())
            true
        }
    }
}