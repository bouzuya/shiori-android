package net.bouzuya.shiori

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import net.bouzuya.shiori.BookmarkListFragmentDirections.Companion.actionBookmarkListFragmentToBookmarkEditFragment
import net.bouzuya.shiori.data.BookmarkAction
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

            mainViewModel.searchQuery.observe(this, Observer { query ->
                if (query != null)
                    viewModel.search(query)
            })

            viewModel.createBookmarkEvent.observe(this, EventObserver {
                findNavController().navigate(
                    actionBookmarkListFragmentToBookmarkEditFragment(
                        0L, null, null
                    )
                )
            })

            viewModel.bookmarkActionEvent.observe(this, EventObserver { (action, bookmark) ->
                when (action) {
                    BookmarkAction.Open -> activity?.packageManager?.let { packageManager ->
                        Intent(Intent.ACTION_VIEW, Uri.parse(bookmark.url)).let { intent ->
                            intent.resolveActivity(packageManager)?.also {
                                startActivity(intent)
                            }
                        }
                    } ?: Toast.makeText(
                        context, "The url can't be opened", Toast.LENGTH_LONG
                    ).show()
                    BookmarkAction.Edit -> findNavController().navigate(
                        actionBookmarkListFragmentToBookmarkEditFragment(
                            bookmark.id, null, null
                        )
                    )
                    BookmarkAction.Share -> activity?.also { activity ->
                        ShareCompat.IntentBuilder.from(activity)
                            .setType("text/plain")
                            .setText(bookmark.url)
                            .setChooserTitle(bookmark.url)
                            .startChooser()
                    }
                }
            })
        }.root
    }
}
