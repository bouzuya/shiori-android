package net.bouzuya.shiori

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
import net.bouzuya.shiori.TagListFragmentDirections.Companion.actionTagListFragmentToBookmarkListFragment
import net.bouzuya.shiori.TagListFragmentDirections.Companion.actionTagListFragmentToTagEditFragment
import net.bouzuya.shiori.databinding.TagListFragmentBinding

class TagListFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: TagListViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TagListViewModel(mainViewModel.tagRepository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TagListFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel

            mainViewModel.editResultEvent.observe(this, EventObserver { isOk ->
                if (isOk)
                    viewModel.refresh()
            })

            viewModel.createTagEvent.observe(this, EventObserver {
                findNavController().navigate(actionTagListFragmentToTagEditFragment(0))
            })

            viewModel.tagActionEvent.observe(this, EventObserver { (action, tag) ->
                when (action) {
                    TagAction.Open -> {
                        mainViewModel.search("tag_id:${tag.id}")
                        findNavController().navigate(actionTagListFragmentToBookmarkListFragment())
                    }
                    TagAction.Edit -> findNavController().navigate(
                        actionTagListFragmentToTagEditFragment(tag.id)
                    )
                }
            })

        }.root
    }
}
