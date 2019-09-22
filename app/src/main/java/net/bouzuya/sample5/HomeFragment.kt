package net.bouzuya.sample5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.bouzuya.sample5.databinding.HomeFragmentBinding
import timber.log.Timber

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(BookmarkRepository(BookmarkDatabase.getDatabase(requireContext()).bookmarkDao())) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return HomeFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel

            viewModel.editBookmarkEvent.observe(this, Observer { bookmark ->
                Timber.d("TODO: click bookmark %d", bookmark.id)
            })
        }.root
    }
}
