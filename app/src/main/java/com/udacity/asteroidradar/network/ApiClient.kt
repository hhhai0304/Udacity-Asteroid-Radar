package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiClient {
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthenticationInterceptor())
        .addInterceptor(HttpLoggingInterceptor())

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .client(httpClient.build())
        .build()

    val asteroidService: AsteroidService by lazy {
        retrofit.create(AsteroidService::class.java)
    }
}

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalUrl = chain.request().url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY).build()

        return chain.proceed(
            chain.request().newBuilder().url(originalUrl).build()
        )
    }
}