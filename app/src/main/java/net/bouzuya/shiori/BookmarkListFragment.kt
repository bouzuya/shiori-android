package net.bouzuya.shiori

import android.content.Intent
import android.net.Uri
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
import net.bouzuya.shiori.BookmarkListFragmentDirections.Companion.actionBookmarkListFragmentToBookmarkEditFragment
import net.bouzuya.shiori.databinding.BookmarkListFragmentBinding

class BookmarkListFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: BookmarkListViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return BookmarkListViewModel(mainViewModel.bookmarkRepository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return BookmarkListFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel

            mainViewModel.editResultEvent.observe(this, EventObserver { isOk ->
                if (isOk)
                    viewModel.refresh()
            })

            viewModel.createBookmarkEvent.observe(this, EventObserver {
                findNavController().navigate(actionBookmarkListFragmentToBookmarkEditFragment(0))
            })

            viewModel.editBookmarkEvent.observe(this, EventObserver { bookmark ->
                findNavController().navigate(
                    actionBookmarkListFragmentToBookmarkEditFragment(
                        bookmark.id
                    )
                )
            })

            viewModel.openBookmarkEvent.observe(this, EventObserver { bookmark ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(bookmark.url)))
            })
        }.root
    }
}
