package net.bouzuya.shiori

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import net.bouzuya.shiori.data.BookmarkDatabase

class SettingFragment : PreferenceFragmentCompat() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: SettingViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingViewModel(
                    mainViewModel.bookmarkRepository,
                    mainViewModel.tagRepository,
                    BuildConfig.VERSION_NAME,
                    BookmarkDatabase.version
                ) as T
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // TODO: data binding

        findAndUpdateSummary("readonly_app_version", viewModel.appVersion)
        findAndUpdateSummary("readonly_database_version", viewModel.databaseVersion)
        viewModel.bookmarkCount.observe(this, Observer { count ->
            if (count != null)
                findAndUpdateSummary("readonly_bookmark_count", count.toString())
        })
        viewModel.tagCount.observe(this, Observer { count ->
            if (count != null)
                findAndUpdateSummary("readonly_tag_count", count.toString())
        })

        findPreference<Preference>("readonly_delete_all")?.setOnPreferenceClickListener {
            viewModel.deleteAll()
            true
        }

        viewModel.deleteCompletedEvent.observe(this, EventObserver {
            // workaround for reloading bookmark / tag list view
            mainViewModel.editResult(true)
        })
    }

    private fun findAndUpdateSummary(key: String, summary: String) {
        findPreference<Preference>(key)?.summary = summary
    }
}
