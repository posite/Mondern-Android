package com.posite.modern.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.posite.modern.data.remote.service.meal.MealService
import com.posite.modern.data.remote.service.shopping.MapService
import com.posite.modern.data.repository.chat.ChatMessageRepository
import com.posite.modern.data.repository.chat.ChatRepository
import com.posite.modern.data.repository.chat.ChatRoomRepository
import com.posite.modern.data.repository.meal.MealRepository
import com.posite.modern.data.repository.shopping.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideChatRepository(auth: FirebaseAuth, fireStore: FirebaseFirestore): ChatRepository =
        ChatRepository(auth, fireStore)

    @Singleton
    @Provides
    fun provideChatRoomRepository(fireStore: FirebaseFirestore): ChatRoomRepository =
        ChatRoomRepository(fireStore)

    @Singleton
    @Provides
    fun provideChatMessageRepository(fireStore: FirebaseFirestore): ChatMessageRepository =
        ChatMessageRepository(fireStore)


    @Singleton
    @Provides
    fun provideShoppingRepository(mapService: MapService): ShoppingRepository =
        ShoppingRepository(mapService)

    @Singleton
    @Provides
    fun provideMealRepository(mealApi: MealService): MealRepository =
        MealRepository(mealApi)
}