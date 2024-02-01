package com.example.coinmarket.view.marketActivity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coinmarket.R
import com.example.coinmarket.model.apiManager.BASE_URL_IMAGE
import com.example.coinmarket.model.CoinsData
import com.example.coinmarket.databinding.ItemRecyclerMarketBinding

class  MarketAdapter(
    private val data: List<CoinsData.Data>,
    private val recyclerCallback: RecyclerCallback
) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    lateinit var binding: ItemRecyclerMarketBinding

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        @SuppressLint("ResourceAsColor")
        fun binViews(dataCoin: CoinsData.Data) {

            binding.txtCoinMain.text = dataCoin.coinInfo.fullName
            if (dataCoin.dISPLAY != null)
                binding.txtPrice.text = dataCoin.dISPLAY.uSD?.pRICE

            if (dataCoin.rAW?.uSD?.cHANGE24HOUR != null) {
                val taghit = dataCoin.rAW.uSD.cHANGE24HOUR

                if (taghit > 0) {

                    binding.txtTaghir.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorGain
                        )
                    )
                    binding.txtTaghir.text =
                        dataCoin.rAW.uSD.cHANGE24HOUR.toString().substring(0, 4) + "%"

                } else if (taghit < 0) {
                    binding.txtTaghir.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorLoss
                        )
                    )
                    binding.txtTaghir.text =
                        dataCoin.rAW.uSD.cHANGE24HOUR.toString().substring(0, 5) + "%"

                } else {
                    binding.txtTaghir.text = "0%"
                }
            }


            val marketCap = dataCoin.rAW?.uSD?.mKTCAP?.div(1000000000)

            val indexDot = marketCap.toString().indexOf('.')
            binding.txtMarketCap.text =
                "$" + dataCoin.rAW?.uSD?.mKTCAP.toString().substring(0, indexDot + 3) + "B "
            Glide
                .with(itemView)
                .load(BASE_URL_IMAGE + dataCoin.coinInfo.imageUrl)
                .into(binding.imgItem)

            itemView.setOnClickListener {

                recyclerCallback.onCoinItemClicked(dataCoin)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRecyclerMarketBinding.inflate(inflater, parent, false)

        return MarketViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {


        holder.binViews(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface RecyclerCallback {

        fun onCoinItemClicked(coinsData: CoinsData.Data) {

        }
    }
}

