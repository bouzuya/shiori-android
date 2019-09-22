package net.bouzuya.sample5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import net.bouzuya.sample5.databinding.EditFragmentBinding


class EditFragment : Fragment() {
    private val viewModel: EditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return EditFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }.root
    }
}
