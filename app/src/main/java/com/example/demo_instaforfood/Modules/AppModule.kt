package com.example.demo_instaforfood.Modules

import android.content.Context
import android.content.SharedPreferences
import com.example.demo_instaforfood.Api.ReviewImageApi
import com.example.demo_instaforfood.Api.UserApi
import com.example.demo_instaforfood.SharedPreferencesHelper
import com.example.demo_instaforfood.Utils.Constants.BASE_URL
import com.example.demo_instaforfood.Utils.Constants.BASE_URL_REVIEW_IMAGES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()


    @Singleton
    @Provides
    @Named("reviewImagesRetrofit")
    fun getRetrofitReviewImages(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL_REVIEW_IMAGES)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun getReviewImagesApi(@Named("reviewImagesRetrofit") retrofit: Retrofit):ReviewImageApi{
        return retrofit.create(ReviewImageApi::class.java)
    }

    @Singleton
    @Provides
    @Named("baseRetrofit")
    fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getUserApi(@Named("baseRetrofit") retrofit: Retrofit):UserApi{
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(sharedPreferences: SharedPreferences): SharedPreferencesHelper {
        return SharedPreferencesHelper(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
    }
}