package com.pascal.irfaan.shoppinglist.di.module

import com.pascal.irfaan.shoppinglist.data.api.ShoppingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ShoppingListApiModule {
    @Singleton
    @Provides
    fun provideShoppingListApi(retrofit : Retrofit)  = retrofit.create(ShoppingApi::class.java)
}