package com.example.demo_instaforfood.Modules

import com.example.demo_instaforfood.Api.ReviewImageAPI
import com.example.demo_instaforfood.Utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun getReviewImagesAPI(retrofit: Retrofit):ReviewImageAPI{
        return retrofit.create(ReviewImageAPI::class.java)
    }
}