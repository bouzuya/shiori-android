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
import net.bouzuya.sample5.TagFragmentDirections.Companion.actionTagFragmentToTagEditFragment
import net.bouzuya.sample5.databinding.TagFragmentBinding


class TagFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: TagViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TagViewModel(mainViewModel.tagRepository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TagFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel

            mainViewModel.editResultEvent.observe(this, EventObserver { isOk ->
                if (isOk)
                    viewModel.refresh()
            })
            mainViewModel.fabClickEvent.observe(this, EventObserver {
                findNavController().navigate(actionTagFragmentToTagEditFragment(0))
            })
            viewModel.editTagEvent.observe(this, EventObserver { tag ->
                findNavController().navigate(actionTagFragmentToTagEditFragment(tag.id))
            })
        }.root
    }
}
