package dev.meboard.app.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import dev.meboard.app.R
import dev.meboard.data.android.MeBoardRepository

class SettingsActivity : AppCompatActivity() {

    private lateinit var repository: MeBoardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = MeBoardRepository(applicationContext)
        supportActionBar?.title = getString(R.string.settings_title)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private lateinit var repository: MeBoardRepository
        private lateinit var prefs: SharedPreferences

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            repository = MeBoardRepository(requireContext().applicationContext)
            prefs = requireContext().getSharedPreferences("meboard_prefs", 0)

            // Wire up clear data
            val clearPref = findPreference<Preference>("clear_learned_data") ?: return
            clearPref.setOnPreferenceClickListener {
                repository.clearLearnedData()
                true
            }

            // Wire up learning pause switch
            val pausePref = findPreference<SwitchPreferenceCompat>("learning_paused") ?: return
            pausePref.isChecked = prefs.getBoolean("learning_paused", false)
            pausePref.setOnPreferenceChangeListener { _, newValue ->
                val paused = newValue as Boolean
                repository.setLearningPaused(paused)
                true
            }
        }
    }
}
