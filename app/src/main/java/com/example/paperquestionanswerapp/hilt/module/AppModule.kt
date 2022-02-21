package com.example.paperquestionanswerapp.hilt.module

import com.example.paperquestionanswerapp.repository.default.CourseAndPaperDetailsRepositoryImpl
import com.example.paperquestionanswerapp.repository.schema.CourseAndPaperDetailsRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Agam on 20-02-2022.
 */

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main as CoroutineDispatcher


    @Singleton
    @Provides
    fun provideCourseAndPaperDetailsRepository(fireStore: FirebaseFirestore) = CourseAndPaperDetailsRepositoryImpl(fireStore) as CourseAndPaperDetailsRepository
}