package com.empresa.kny.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DemonSlayerNetworkModule {

    @Singleton
    @Provides

    fun provideRetrofitDemonSlayer(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://demon-slayer-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDemonSlayerApi(retrofit: Retrofit): DemonSlayerApi {
        return retrofit.create(DemonSlayerApi::class.java)
    }
}