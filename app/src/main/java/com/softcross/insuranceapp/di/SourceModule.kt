package com.softcross.insuranceapp.di

import com.softcross.insuranceapp.data.source.RemoteDataSource
import com.softcross.insuranceapp.data.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

}