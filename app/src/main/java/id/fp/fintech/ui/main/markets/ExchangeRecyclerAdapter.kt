package id.fp.fintech.ui.main.markets

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.fp.core.domain.model.Exchange
import id.fp.fintech.R
import id.fp.fintech.databinding.AdapterExchangeCryptoBinding

interface ExchangeOnClickListener {

    fun openExchangeDetails(exchange: Exchange)

}


class ExchangeRecyclerViewAdapter(private val listener: ExchangeOnClickListener) :
        RecyclerView.Adapter<ExchangeRecyclerViewAdapter.ExchangeViewHolder>() {

    private lateinit var binding: AdapterExchangeCryptoBinding
    var exchangesList: List<Exchange> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        binding = AdapterExchangeCryptoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ExchangeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return exchangesList.size
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        val exchange = exchangesList[position]
        holder.tvExchangeName.text = exchange.name
        holder.tvYear.text = exchange.year_established.toString()
        holder.tvTrustScore.text = exchange.trust_score.toString()
        holder.tvRank.text = exchange.trust_score_rank.toString()
        holder.tvLocation.text = exchange.country

        Glide.with(holder.itemView.context).load(exchange.image)
                .placeholder(R.drawable.ic_baseline_monetization_on_gray_24)
                .error(R.drawable.ic_baseline_monetization_on_gray_24)
                .apply(RequestOptions().centerCrop())
                .into(holder.image)

        holder.container.setOnClickListener {
            listener.openExchangeDetails(exchange)
        }
    }

    fun setExchangeListItems(exchangesList: ArrayList<Exchange>) {
        this.exchangesList = emptyList()
        this.exchangesList = exchangesList
        notifyDataSetChanged()
    }

    class ExchangeViewHolder(binding: AdapterExchangeCryptoBinding) :
            RecyclerView.ViewHolder(binding.root) {

        val container: ConstraintLayout = binding.layoutContainer
        val tvExchangeName: TextView = binding.textName
        val tvRank: TextView = binding.textRank
        val tvYear: TextView = binding.textYear
        val tvTrustScore: TextView = binding.textTrustScore
        val tvLocation: TextView = binding.textLocation
        val image: ImageView = binding.imgIcon
    }
}
