package com.pascal.irfaan.shoppinglist.di.module

import com.pascal.irfaan.shoppinglist.data.repositories.ItemsRepository
import com.pascal.irfaan.shoppinglist.data.repositories.impl.ItemRepositoriesImpl
import com.pascal.irfaan.shoppinglist.di.qualifier.ShoppingListRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named


@Module
@InstallIn(ViewModelComponent::class)
abstract class ShoppingListRepoModule {

    @Binds
    @ShoppingListRepo
    abstract fun bindsRepoShoppingList(itemRepositoriesImpl: ItemRepositoriesImpl): ItemsRepository
}