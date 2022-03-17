package id.fp.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.fp.core.databinding.ItemSampleBinding
import id.fp.core.domain.model.Sample
import id.fp.core.utils.OnItemClicked
import id.fp.core.utils.loadImageFull


class SampleAdapter(
    private val onItemClicked: OnItemClicked
) : RecyclerView.Adapter<SampleAdapter.EventHolder>() {

    var list = ArrayList<Sample>()

    fun addList(listData: List<Sample>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            DiffCallback(
                list,
                listData
            )
        )
        list.clear()
        list.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = ItemSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(onItemClicked, binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(onItemClicked, list[position])
    }

    override fun getItemCount(): Int = list.size

    class EventHolder(val onItemClicked: OnItemClicked, private val binding: ItemSampleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onItemClicked: OnItemClicked, data: Sample) {
            with(binding) {
                tvTitle.text = data.name
                ivCategory.loadImageFull(data.youtubeUrl.toString())
            }
            binding.root.setOnClickListener {
                onItemClicked.onEventClick(data)
            }
        }
    }
}
