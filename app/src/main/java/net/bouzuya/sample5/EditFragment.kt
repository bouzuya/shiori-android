package net.bouzuya.sample5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import net.bouzuya.sample5.databinding.EditFragmentBinding


class EditFragment : DialogFragment() {
    private val args: EditFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: EditViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EditViewModel(
                    mainViewModel.bookmarkRepository,
                    args.bookmarkId
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return EditFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel

            viewModel.cancelEvent.observe(this, EventObserver {
                mainViewModel.editResult(false)
                dismiss()
            })
            viewModel.okEvent.observe(this, EventObserver {
                mainViewModel.editResult(true)
                dismiss()
            })
        }.root
    }
}
