package net.bouzuya.shiori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import net.bouzuya.shiori.databinding.LicenseFragmentBinding

class LicenseFragment : Fragment() {
    private val viewModel: LicenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = LicenseFragmentBinding.inflate(inflater, container, false).also { binding ->
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.licenseWebView.loadUrl("file:///android_asset/licenses.html")
    }.root
}
