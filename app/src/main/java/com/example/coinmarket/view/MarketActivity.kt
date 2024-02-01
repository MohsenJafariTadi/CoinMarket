package com.example.coinmarket.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinmarket.model.apiManager.MainViewModel
import com.example.coinmarket.model.CoinsData
import com.example.coinmarket.databinding.ActivityMarketBinding

import com.example.coinmarket.model.apiManager.ResourceModel
import com.example.coinmarket.view.marketActivity.MarketAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MarketActivity : AppCompatActivity(), MarketAdapter.RecyclerCallback {

    lateinit var binding: ActivityMarketBinding
    lateinit var dataNews: ArrayList<Pair<String, String>>

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.layoutWatchlist.btnShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }

        binding.swipeRefreshMain.setOnRefreshListener {
            refresh()

            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }

    }


    fun refresh() {
        viewModel.getNewsRep()
        viewModel.getCoinRep()
    }

    private fun init() {
        getNewsFromApi()
        getTopCoinsFromApi()
    }

    private fun getNewsFromApi() {
        lifecycleScope.launch {
            viewModel.listNewsStateFlow.collect {
                when (it) {
                    is ResourceModel.Error -> {
                        binding.layoutWatchlist.determinateBar.isVisible = false
                        Toast.makeText(applicationContext,
                            "Error ${it.message}",
                            Toast.LENGTH_SHORT).show();
                        binding.mainLayout.isVisible = false
                        binding.layoutTry.isVisible = true


                    }
                    is ResourceModel.Loading -> {
                        binding.layoutWatchlist.determinateBar.isVisible = true
                        binding.layoutTry.isVisible = false
                    }
                    is ResourceModel.Success -> {

                        binding.layoutWatchlist.determinateBar.isVisible = false
                        binding.layoutNews.txtNews.text = it.data?.data?.random()?.title.toString()
                        binding.mainLayout.isVisible = true
                        binding.layoutTry.isVisible = false
                    }
                }
            }
        }


        viewModel.getNewsRep()
    }

    private fun getTopCoinsFromApi() {
        lifecycleScope.launch {
            viewModel.listCoins.collect {
                when (it) {
                    is ResourceModel.Error -> {
                        Log.e("gorgali2", it.data.toString())
                        binding.layoutWatchlist.determinateBar.isVisible = false
                        Toast.makeText(applicationContext,
                            "Error ${it.message}",
                            Toast.LENGTH_SHORT).show();

                        binding.mainLayout.visibility = View.GONE
                        binding.layoutTry.visibility = View.VISIBLE

                         btnTry()

                    }
                    is ResourceModel.Loading -> {
                        binding.layoutWatchlist.determinateBar.isVisible = true

                    }
                    is ResourceModel.Success -> {
                        Log.e("gorgali1", it.data.toString())
                        binding.layoutWatchlist.determinateBar.isVisible = false
                        showDataInRecycler(it.data!!.data)
                        binding.mainLayout.isVisible = true
                        binding.layoutTry.isVisible = false
                    }
                }
            }
        }

        viewModel.getCoinRep()
    }

    fun btnTry(){
        binding.imgRef.setOnClickListener {
            getNewsFromApi()
            getTopCoinsFromApi()
        }
    }
    private fun showDataInRecycler(data: List<CoinsData.Data>) {
        val marketAdapter = MarketAdapter(data, this)
        binding.layoutWatchlist.recyclerMain.adapter = marketAdapter
        binding.layoutWatchlist.recyclerMain.layoutManager = LinearLayoutManager(this)
    }


}


