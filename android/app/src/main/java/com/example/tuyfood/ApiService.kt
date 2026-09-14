package com.example.tuyfood

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String?,
    val image: String?,
    val available: Boolean
)

data class LoginResponse(
    val userId: Long,
    val message: String,
    val role: String
)

data class ChatResponse(
    val reply: String
)

interface ApiService {
    @GET("api/products")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("api/products/search")
    suspend fun searchProducts(@Query("keyword") keyword: String): List<ProductResponse>

    @POST("api/auth/login")
    suspend fun login(@Body body: Map<String, String>): LoginResponse

    @POST("api/chat")
    suspend fun chat(@Body body: Map<String, String>): ChatResponse
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.165:8081/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Accept-Charset", "utf-8")
                .build()
            chain.proceed(request)
        }
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}