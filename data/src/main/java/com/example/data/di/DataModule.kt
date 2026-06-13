package com.example.data.di

import androidx.room.Room
import com.example.data.GolfApi
import com.example.data.NetworkConfig
import com.example.data.connectivity.ConnectivityObserver
import com.example.data.connectivity.ConnectivityObserverImpl
import com.example.data.local.database.GolfDatabase
import com.example.data.repository.GolfRepositoryImpl
import com.example.domain.repository.GolfRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {

    single<ConnectivityObserver> {
        ConnectivityObserverImpl(androidContext())
    }

    single {
        Moshi.Builder().build()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<GolfApi> {
        get<Retrofit>().create(GolfApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            GolfDatabase::class.java,
            GolfDatabase.DATABASE_NAME,
        ).build()
    }

    single {
        get<GolfDatabase>().golfDao()
    }

    single<GolfRepository> {
        GolfRepositoryImpl(
            golfApi = get(),
            golfDao = get(),
        )
    }
}
