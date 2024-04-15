package au.com.agl.kotlincats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.agl.kotlincats.databinding.ActivityMainBinding
import au.com.agl.kotlincats.presentation.adapter.DataItem
import au.com.agl.kotlincats.presentation.adapter.MyAdapter
import au.com.agl.kotlincats.presentation.dialogs.Dialogs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CatViewModel by viewModels()

    private val adapter: MyAdapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()
        viewModel.loadCatOwners()
        viewModel.owners.observe(this) { state ->
            when (state.status) {
                Status.SUCCESS -> {
                    binding.progressIndicator.visibility = View.GONE
                    setUpData(state.data)
                }

                Status.LOADING -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    binding.progressIndicator.visibility = View.GONE
                    Dialogs.displayError(this, state.message)
                }
            }
        }
    }

    private fun recyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }

    private fun setUpData(data: List<DataItem>?) {
        data?.let {
            adapter.setData(it)
        }
    }
}
