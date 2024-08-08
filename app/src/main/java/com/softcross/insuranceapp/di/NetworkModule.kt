package com.softcross.insuranceapp.di

import com.softcross.insuranceapp.BuildConfig
import com.softcross.insuranceapp.data.api.LocationService
import com.softcross.insuranceapp.data.api.CarPropertiesService
import com.softcross.insuranceapp.data.api.PolicyTypeService
import com.softcross.insuranceapp.data.api.CustomerService
import com.softcross.insuranceapp.data.api.PaymentService
import com.softcross.insuranceapp.data.api.PolicyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @ViewModelScoped
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @ViewModelScoped
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Provides
    @ViewModelScoped
    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    @Named("addressRetrofit")
    fun createAddressRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CITY_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    @Named("carPropertiesRetrofit")
    fun createCarPropertiesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CAR_PROPERTIES_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideCarPropertiesService(@Named("carPropertiesRetrofit") retrofit: Retrofit): CarPropertiesService {
        return retrofit.create(CarPropertiesService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideAddressService(@Named("addressRetrofit") retrofit: Retrofit): LocationService {
        return retrofit.create(LocationService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideCustomerService(retrofit: Retrofit): CustomerService {
        return retrofit.create(CustomerService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providePaymentService(retrofit: Retrofit): PaymentService {
        return retrofit.create(PaymentService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providePolicyService(retrofit: Retrofit): PolicyService {
        return retrofit.create(PolicyService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideCarService(retrofit: Retrofit): PolicyTypeService {
        return retrofit.create(PolicyTypeService::class.java)
    }

}