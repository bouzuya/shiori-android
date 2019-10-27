package net.bouzuya.shiori

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import net.bouzuya.shiori.MainNavGraphDirections.Companion.actionGlobalBookmarkEditFragment
import net.bouzuya.shiori.data.BookmarkDatabase
import net.bouzuya.shiori.data.BookmarkRepository
import net.bouzuya.shiori.data.TagRepository
import net.bouzuya.shiori.databinding.ActivityMainBinding
import timber.log.Timber

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
                            setOf(
                                R.id.bookmark_list_fragment,
                                R.id.tag_list_fragment,
                                R.id.setting_fragment
                            ),
                            binding.mainDrawerLayout
                        )
                    )
                }
                binding.mainNavigationView.setupWithNavController(findNavController())

                findNavController().addOnDestinationChangedListener { _, destination, _ ->
                    val new = when (destination.id) {
                        R.id.bookmark_edit_fragment,
                        R.id.bookmark_list_fragment -> true
                        else -> false
                    }
                    if (new) viewModel.showSearchIcon() else viewModel.hideSearchIcon()
                    invalidateOptionsMenu()
                }

                ShareCompat.IntentReader.from(this)?.let { intentReader ->
                    if (intentReader.isSingleShare && intentReader.type == "text/plain") intentReader
                    else null
                }?.let { intentReader ->
                    intentReader.text?.toString()?.let { url ->
                        val title = intentReader.subject ?: ""
                        // title is nullable (use empty string if title is null)
                        // url is non nullable
                        Pair(title, url)
                    }
                }?.also { (title, url) ->
                    if (findNavController().currentDestination?.id != R.id.bookmark_edit_fragment) {
                        findNavController().navigate(
                            actionGlobalBookmarkEditFragment(
                                0L, title, url
                            )
                        )
                    }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!viewModel.isVisibleSearchIcon) return true
        menuInflater.inflate(R.menu.main_toolbar, menu)
        menu.findItem(R.id.main_toolbar_search)?.let { menuItem ->
            (menuItem.actionView as? SearchView)?.let { searchView ->
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        Timber.d("onQueryTextSubmit: $query")
                        return false
                    }

                    override fun onQueryTextChange(query: String): Boolean {
                        Timber.d("onQueryTextChange: $query")
                        viewModel.search(query)
                        return false
                    }
                })
                viewModel.searchQuery.value?.let { query ->
                    if (query.isNotEmpty()) {
                        menuItem.expandActionView() // reset query = ""
                        searchView.setQuery(query, true)
                    }
                }
            }
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp()
    }

    private fun findNavController(): NavController = findNavController(R.id.main_nav_host_fragment)
}
