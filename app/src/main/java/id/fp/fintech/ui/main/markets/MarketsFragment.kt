package id.fp.fintech.ui.main.markets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.fp.core.domain.model.Coin
import id.fp.core.domain.model.DataState
import id.fp.core.domain.model.Exchange
import id.fp.fintech.R
import id.fp.fintech.databinding.FragmentMarketsBinding
import id.fp.fintech.ui.base.BaseFragment
import id.fp.fintech.ui.main.home.CoinCardOnClickListener
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup
import org.koin.android.ext.android.inject

class MarketsFragment : BaseFragment(), CoinCardOnClickListener, ExchangeOnClickListener {

    // ViewModel
    private val viewModel: MarketViewModel by inject()

    // Listener
    private var coinOnClickListener: CoinCardOnClickListener = this
    private var exchangeOnClickListener: ExchangeOnClickListener = this

    // View Binding
    private var _binding: FragmentMarketsBinding? = null
    private val binding get() = _binding!!

    // Views
    private lateinit var recyclerViewMarket: RecyclerView
    private lateinit var recyclerViewExchange: RecyclerView
    lateinit var marketRecyclerAdapter: MarketRecyclerViewAdapter
    lateinit var exchangeRecyclerAdapter: ExchangeRecyclerViewAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout
    lateinit var btnRetry: AppCompatButton
    lateinit var layoutBadState: View
    lateinit var textState: TextView
    lateinit var imgState: ImageView
    private lateinit var themedButtonGroup: ThemedToggleButtonGroup
    private lateinit var btnCrypto: ThemedButton
    private lateinit var btnExchange: ThemedButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketsBinding.inflate(inflater, container, false)

        val view = binding.root

        subscribeObservers()

        initViews()
        initRecyclerViews()

        fetchMarkets()

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchMarkets() {
        viewModel.getCoins("usd")
        recyclerViewMarket.scheduleLayoutAnimation()
    }

    private fun fetchExchanges() {
        viewModel.getExchanges()
        recyclerViewExchange.scheduleLayoutAnimation()
    }

    private fun showInternetConnectionErrorLayout() {
        if (marketRecyclerAdapter.itemCount > 0 || exchangeRecyclerAdapter.itemCount > 0) {
            showErrorDialog(
                getString(R.string.network_error),
                getString(R.string.check_internet)
            )
        } else {
            layoutBadState.isVisible = true
            textState.text = getString(R.string.internet_connection_error)
            btnRetry.isVisible = true
        }
    }

    private fun showBadStateLayout() {
        if (marketRecyclerAdapter.itemCount > 0 || exchangeRecyclerAdapter.itemCount > 0) {
            showErrorDialog(
                getString(com.thecode.aestheticdialogs.R.string.error),
                getString(R.string.service_unavailable)
            )
        } else {
            layoutBadState.isVisible = true
            textState.text = getString(R.string.no_result_found)
            btnRetry.isVisible = true
        }
    }

    private fun showRecyclerViewMarket(state: Boolean) {
        recyclerViewMarket.isVisible = state
        recyclerViewExchange.isVisible = !state
    }


    private fun hideBadStateLayout() {
        layoutBadState.isVisible = false
    }

    private fun subscribeObservers() {
        viewModel.coinState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    hideBadStateLayout()
                    hideLoadingProgress()
                    showRecyclerViewMarket(true)
                    populateRecyclerViewMarket(it.data)
                }
                is DataState.Loading -> {
                    showLoadingProgress()
                }
                is DataState.Error -> {
                    hideLoadingProgress()
                    showInternetConnectionErrorLayout()
                    Toast.makeText(
                        activity,
                        getString(R.string.internet_connection_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.exchangeState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    hideBadStateLayout()
                    hideLoadingProgress()
                    showRecyclerViewMarket(false)
                    populateRecyclerViewExchange(it.data)
                }
                is DataState.Loading -> {
                    showLoadingProgress()
                }
                is DataState.Error -> {
                    hideLoadingProgress()
                    showInternetConnectionErrorLayout()
                    Toast.makeText(
                        activity,
                        getString(R.string.internet_connection_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun hideLoadingProgress() {
        refreshLayout.isRefreshing = false
    }

    private fun showLoadingProgress() {
        refreshLayout.isRefreshing = true
    }

    private fun initRecyclerViews() {
        //Crypto Market
        marketRecyclerAdapter = MarketRecyclerViewAdapter(coinOnClickListener)
        recyclerViewMarket.layoutManager = LinearLayoutManager(activity)
        recyclerViewMarket.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerViewMarket.adapter = SlideInBottomAnimationAdapter(marketRecyclerAdapter)

        //Exchanges
        exchangeRecyclerAdapter = ExchangeRecyclerViewAdapter(exchangeOnClickListener)
        recyclerViewExchange.layoutManager = LinearLayoutManager(activity)
        recyclerViewExchange.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerViewExchange.adapter = SlideInBottomAnimationAdapter(exchangeRecyclerAdapter)
    }

    private fun initViews() {
        refreshLayout = binding.refreshLayout
        recyclerViewMarket = binding.recyclerviewCryptoMarkets
        recyclerViewExchange = binding.recyclerviewCryptoExchanges
        btnRetry = binding.included.btnRetry
        layoutBadState = binding.included.layoutBadState
        imgState = binding.included.imgState
        textState = binding.included.textState
        themedButtonGroup = binding.themedButtonGroup
        btnCrypto = binding.btnCrypto
        btnExchange = binding.btnExchange


        btnRetry.setOnClickListener { if (btnCrypto.isSelected) fetchMarkets() else fetchExchanges() }

        refreshLayout.setOnRefreshListener {
            if (btnCrypto.isSelected) fetchMarkets() else fetchExchanges()
        }

        themedButtonGroup.selectButton(btnCrypto)

        themedButtonGroup.setOnSelectListener {
            when (it) {
                btnCrypto -> fetchMarkets()
                btnExchange -> fetchExchanges()
            }
        }
    }

    private fun populateRecyclerViewMarket(coins: List<Coin>) {
        if (coins.isEmpty()) {
            showBadStateLayout()
        } else {
            val coinArrayList: ArrayList<Coin> = ArrayList()
            for (i in coins.indices) {
                val coin = coins[i]
                coinArrayList.add(coin)
                marketRecyclerAdapter.setCoinListItems(coinArrayList)
                recyclerViewMarket.scheduleLayoutAnimation()
            }
        }
    }

    private fun populateRecyclerViewExchange(exchanges: List<Exchange>) {
        if (exchanges.isEmpty()) {
            showBadStateLayout()
        } else {
            val exchangeArrayList: ArrayList<Exchange> = ArrayList()
            for (i in exchanges.indices) {
                val exchange = exchanges[i]
                exchangeArrayList.add(exchange)
                exchangeRecyclerAdapter.setExchangeListItems(exchangeArrayList)
                recyclerViewExchange.scheduleLayoutAnimation()
            }
        }
    }


    override fun openCoinDetails(coin: Coin) {
        openCoinDetailsActivity(coin)
    }

    override fun openExchangeDetails(exchange: Exchange) {
        openExchangeDetailsActivity(exchange)
    }


}
