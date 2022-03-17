package id.fp.fintech.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import id.candlekeeper.core.data.Resource
import id.fp.core.adapter.SampleAdapter
import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.domain.model.Sample
import id.fp.core.utils.OnItemClicked
import id.fp.core.utils.PrefManager
import id.fp.fintech.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClicked {

    private val viewModel: MainViewModel by viewModel()
    private val prefManager: PrefManager by inject()

    private lateinit var sampleAdapter: SampleAdapter

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        observeData()
    }

    private fun initView() {
        with(binding.rvSample) {
            sampleAdapter = SampleAdapter(this@MainActivity)
            layoutManager = GridLayoutManager(this@MainActivity, 1, GridLayoutManager.VERTICAL, false)
            adapter = sampleAdapter
        }
    }

    private fun observeData() {
        viewModel.getSample().observe(this, sample)
        viewModel.getSampleDb().observe(this, sampleDb)
    }

    private val sample = Observer<ApiResponse<List<Sample>>> { data ->
        when (data) {
            is ApiResponse.Loading -> {
            }
            is ApiResponse.Success -> {
            }
            is ApiResponse.Error -> {
            }
            is ApiResponse.Empty -> {
            }
        }
    }

    private val sampleDb = Observer<Resource<List<Sample>>> { data ->
        when (data) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
            }
            is Resource.Error -> {
            }
        }
    }

    override fun onEventClick(data: Sample) {
        super.onEventClick(data)
        //item click sample
    }
}