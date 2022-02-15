package com.example.prettyshelf.networking

import com.example.prettyshelf.BuildConfig
import com.example.prettyshelf.data.OpenLibraryApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*

@Module
class NetworkModule {
    private val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    @Provides
    fun provideApiRemoteRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            val requestWithUserAgent = chain.request().newBuilder()
                .header("Date", date)
                .build()
            chain.proceed(requestWithUserAgent)
        }
        return okHttpBuilder.build()
    }

    @Provides
    fun provideOpenAPIService(): OpenLibraryApi {
        return provideApiRemoteRetrofit(provideHttpClient()).create(OpenLibraryApi::class.java)
    }
}