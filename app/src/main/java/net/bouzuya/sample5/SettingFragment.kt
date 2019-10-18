package net.bouzuya.sample5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.bouzuya.sample5.data.BookmarkDatabase
import net.bouzuya.sample5.databinding.SettingFragmentBinding


class SettingFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: SettingViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingViewModel(
                    mainViewModel.bookmarkRepository,
                    BuildConfig.VERSION_NAME,
                    BookmarkDatabase.version
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return SettingFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }.root
    }
}
