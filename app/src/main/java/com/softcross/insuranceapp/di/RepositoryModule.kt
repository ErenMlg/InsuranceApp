package com.softcross.insuranceapp.di

import com.softcross.insuranceapp.data.repository.PolicyTypeRepositoryImpl
import com.softcross.insuranceapp.data.repository.CustomerRepositoryImpl
import com.softcross.insuranceapp.data.repository.FirebaseRepositoryImpl
import com.softcross.insuranceapp.data.repository.PaymentRepositoryImpl
import com.softcross.insuranceapp.data.repository.PolicyRepositoryImpl
import com.softcross.insuranceapp.data.repository.VehicleAndLocationRepositoryImpl
import com.softcross.insuranceapp.domain.repository.PolicyTypeRepository
import com.softcross.insuranceapp.domain.repository.CustomerRepository
import com.softcross.insuranceapp.domain.repository.FirebaseRepository
import com.softcross.insuranceapp.domain.repository.PaymentRepository
import com.softcross.insuranceapp.domain.repository.PolicyRepository
import com.softcross.insuranceapp.domain.repository.VehicleAndLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    @ViewModelScoped
    abstract fun provideCustomerRepository(customerRepositoryImpl: CustomerRepositoryImpl): CustomerRepository

    @Binds
    @ViewModelScoped
    abstract fun provideVehicleAndLocationRepository(vehicleAndLocationRepositoryImpl: VehicleAndLocationRepositoryImpl): VehicleAndLocationRepository

    @Binds
    @ViewModelScoped
    abstract fun providePolicyRepository(policyRepositoryImpl: PolicyRepositoryImpl): PolicyRepository

    @Binds
    @ViewModelScoped
    abstract fun provideCarRepository(carRepositoryImpl: PolicyTypeRepositoryImpl): PolicyTypeRepository

    @Binds
    @ViewModelScoped
    abstract fun providePaymentRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository

}