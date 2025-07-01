package com.example.assignment.di

import com.example.mvidemo.domain.ProductRepository
import com.example.mvidemo.data.remote.ApiService
import com.example.mvidemo.data.repository.ProductRepositoryImpl
import com.example.mvidemo.domain.usecase.GetProductsUseCase
import com.example.mvidemo.domain.usecase.GetProductsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =  Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideRepository(apiService: ApiService): ProductRepository = ProductRepositoryImpl(apiService)

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideGetProductUseCase(repository: ProductRepository): GetProductsUseCase =
        GetProductsUseCaseImpl(repository)
}