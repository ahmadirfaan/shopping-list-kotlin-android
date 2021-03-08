package com.pascal.irfaan.shoppinglist.di.module

import com.pascal.irfaan.shoppinglist.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShoppingListAppModule {

    @Singleton
    @Provides
    fun provideGsonConverter() : GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofitInstance(gson : GsonConverterFactory) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(gson).build()
    }
}