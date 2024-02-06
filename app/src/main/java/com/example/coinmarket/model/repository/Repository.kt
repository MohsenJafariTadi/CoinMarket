package com.example.coinmarket.model.repository

import android.util.Log
import com.example.coinmarket.model.CoinsData
import com.example.coinmarket.model.NewsData
import com.example.coinmarket.model.apiManager.ApiService
import com.example.coinmarket.model.apiManager.BASE_URL
import com.example.coinmarket.model.apiManager.ResourceModel
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class Repository(private var apiService: ApiService) {


    suspend fun getNewsList(): Flow<ResourceModel<NewsData>> = response(apiService.getTopNews())


    suspend fun getCoinList(): Flow<ResourceModel<CoinsData>> = response(apiService.getTopCoins())


    suspend fun <T> response(call: Call<T>): Flow<ResourceModel<T>> = flow {

        try {
            val result = call.execute()

            if (result.isSuccessful) {

                emit(ResourceModel.Success(result.body()))

            } else {

                emit(ResourceModel.Error(result.message()))
            }
        } catch (e: HttpException) {
            emit(ResourceModel.Error("Network Connection Failed"))
        } catch (e: Exception) {
            emit(ResourceModel.Error("Could Not Connect To Server"))
        }
    }


}


