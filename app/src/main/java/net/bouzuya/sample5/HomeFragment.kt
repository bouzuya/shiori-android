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
import androidx.navigation.fragment.findNavController
import net.bouzuya.sample5.HomeFragmentDirections.Companion.actionHomeFragmentToBookmarkEditFragment
import net.bouzuya.sample5.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(mainViewModel.bookmarkRepository) as T
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

            mainViewModel.editResultEvent.observe(this, EventObserver { isOk ->
                if (isOk)
                    viewModel.refresh()
            })

            viewModel.createBookmarkEvent.observe(this, EventObserver {
                findNavController().navigate(actionHomeFragmentToBookmarkEditFragment(0))
            })

            viewModel.editBookmarkEvent.observe(this, EventObserver { bookmark ->
                findNavController().navigate(actionHomeFragmentToBookmarkEditFragment(bookmark.id))
            })
        }.root
    }
}
