package net.bouzuya.shiori

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import net.bouzuya.shiori.data.BookmarkDatabase
import net.bouzuya.shiori.data.BookmarkRepository
import net.bouzuya.shiori.data.TagRepository
import net.bouzuya.shiori.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        // FIXME
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val database = BookmarkDatabase.getDatabase(applicationContext)
                return MainViewModel(
                    BookmarkRepository(database.bookmarkDao(), database.bookmarkTagJoinDao()),
                    TagRepository(database.tagDao())
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .also { binding ->
                binding.lifecycleOwner = this
                binding.viewModel = viewModel

                binding.mainToolbar.let { toolbar ->
                    setSupportActionBar(toolbar)
                    toolbar.setupWithNavController(
                        findNavController(),
                        AppBarConfiguration(
                            setOf(R.id.bookmark_list_fragment, R.id.tag_list_fragment),
                            binding.mainDrawerLayout
                        )
                    )
                }
                binding.mainNavigationView.setupWithNavController(findNavController())
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp()
    }

    private fun findNavController(): NavController = findNavController(R.id.main_nav_host_fragment)
}
