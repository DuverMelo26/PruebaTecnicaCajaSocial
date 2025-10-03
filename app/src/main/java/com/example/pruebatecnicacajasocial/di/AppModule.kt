package com.example.pruebatecnicacajasocial.di

import android.content.Context
import androidx.room.Room
import com.example.pruebatecnicacajasocial.core.database.AppDatabase
import com.example.pruebatecnicacajasocial.core.database.daos.MyCharactersDao
import com.example.pruebatecnicacajasocial.core.network.NetworkInterceptor
import com.example.pruebatecnicacajasocial.core.network.RickAndMortyApi
import com.example.pruebatecnicacajasocial.characters.data.repository.CharactersLocalRepository
import com.example.pruebatecnicacajasocial.characters.data.repository.CharactersRemoteRepository
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersRemoteRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRickAndMortyApi(retrofit: Retrofit) : RickAndMortyApi =
        retrofit.create(RickAndMortyApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty_db"
        ).build()
    }

    @Provides
    fun provideMyCharactersDao(database: AppDatabase) : MyCharactersDao = database.myCharacterDao()

    @Singleton
    @Provides
    fun provideCharactersRemoteRepositoryInterface(
        charactersRemoteRepository: CharactersRemoteRepository
    ) : CharactersRemoteRepositoryInterface = charactersRemoteRepository

    @Singleton
    @Provides
    fun provideCharactersLocalRepositoryInterface(
        charactersLocalRepository: CharactersLocalRepository
    ) : CharactersLocalRepositoryInterface = charactersLocalRepository
}