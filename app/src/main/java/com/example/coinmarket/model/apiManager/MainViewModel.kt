package com.example.coinmarket.model.apiManager


import androidx.lifecycle.ViewModel
import com.example.coinmarket.model.CoinsData
import com.example.coinmarket.model.NewsData
import androidx.lifecycle.viewModelScope
import com.example.coinmarket.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {


    private var _listNews = MutableStateFlow<ResourceModel<NewsData>>(ResourceModel.Loading())
    val listNewsStateFlow: StateFlow<ResourceModel<NewsData>>
        get()=_listNews

    private val _listCoins = MutableStateFlow<ResourceModel<CoinsData>>(ResourceModel.Loading())
    val listCoins: StateFlow<ResourceModel<CoinsData>>
        get()=_listCoins

    fun  getCoinRep() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCoinList().collect {
                _listCoins.emit(it)
            }
        }
    }
    fun  getNewsRep(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNewsList().collect {
                _listNews.emit(it)
            }
        }
    }


}

