package id.fp.fintech.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import id.candlekeeper.core.data.Resource
import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.domain.model.Sample
import id.fp.fintech.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
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
}