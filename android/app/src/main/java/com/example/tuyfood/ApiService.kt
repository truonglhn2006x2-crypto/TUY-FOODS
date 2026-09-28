package com.example.tuyfood

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.PATCH
import java.util.concurrent.TimeUnit

data class CategoryResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val image: String?
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String?,
    val image: String?,
    val rating: Double?,
    val available: Boolean,
    val category: CategoryResponse?
)

data class LoginResponse(
    val userId: Long,
    val message: String,
    val role: String
)

data class ChatResponse(
    val reply: String
)

data class OrderRequest(
    val userId: Long,
    val deliveryAddress: String,
    val phone: String,
    val orderDiscount: Int,
    val shippingDiscount: Int,
    val orderVoucherCode: String?,
    val shippingVoucherCode: String?,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    val productId: Long,
    val quantity: Int
)

data class OrderApiResponse(
    val id: Long,
    val totalAmount: Double,
    val discountAmount: Double,
    val status: String,
    val deliveryAddress: String?,
    val phone: String?,
    val note: String?,
    val paymentMethod: String,
    val createdAt: String,
    val items: List<OrderItemResponse>
)

data class OrderItemResponse(
    val id: Long,
    val product: ProductResponse,
    val productName: String,
    val price: Double,
    val quantity: Int
)

data class MiniGameResultResponse(
    val id: Long,
    val score: Int,
    val reward: Int,
    val rewardCode: String?,
    val createdAt: String
)

data class MiniGameErrorResponse(
    val message: String
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String?,
    val address: String?,
    val points: Int,
    val miniGameKeys: Int
)

interface ApiService {

    @GET("api/users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Long
    ): UserResponse

    @POST("api/users/{userId}/minigame-key")
    suspend fun addMiniGameKey(
        @Path("userId") userId: Long
    ): UserResponse

    @POST("api/minigame/exchange")
    suspend fun exchangePointsForKeys(
        @Query("userId") userId: Long,
        @Query("keys") keys: Int
    ): UserResponse

    @POST("api/minigame/play")
    suspend fun playMiniGame(
        @Query("userId") userId: Long,
        @Query("gameId") gameId: Long
    ): MiniGameResultResponse

    @GET("api/minigame/results/{userId}")
    suspend fun getMiniGameResults(
        @Path("userId") userId: Long
    ): List<MiniGameResultResponse>

    @GET("api/products")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("api/categories")
    suspend fun getAllCategories(): List<CategoryResponse>

    @GET("api/products/search")
    suspend fun searchProducts(
        @Query("keyword") keyword: String
    ): List<ProductResponse>

    @POST("api/auth/login")
    suspend fun login(
        @Body body: Map<String, String>
    ): LoginResponse

    @POST("api/auth/register")
    suspend fun register(
        @Body body: Map<String, String>
    ): Map<String, Any>

    @POST("api/chat")
    suspend fun chat(
        @Body body: Map<String, String>
    ): ChatResponse

    @POST("api/orders")
    suspend fun createOrder(
        @Body body: OrderRequest
    ): OrderApiResponse

    @GET("api/orders/user/{userId}")
    suspend fun getUserOrders(
        @Path("userId") userId: Long
    ): List<OrderApiResponse>

    @GET("api/orders")
    suspend fun getAllOrders(): List<OrderApiResponse>

    @PATCH("api/orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") orderId: Long,
        @Query("status") status: String
    ): OrderApiResponse

    @GET("api/membership/{userId}")
    suspend fun getMembership(
        @Path("userId") userId: Long
    ): MembershipResponse
}

object RetrofitClient {

    /*
     * Nếu chạy bằng Android Emulator:
     * dùng http://10.0.2.2:8081/
     *
     * Nếu chạy bằng điện thoại thật:
     * dùng địa chỉ IP Wi-Fi của laptop.
     */
    private const val BASE_URL =
        "http://192.168.1.165:8081/"

    private val okHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(
                30,
                TimeUnit.SECONDS
            )
            .readTimeout(
                30,
                TimeUnit.SECONDS
            )
            .addInterceptor { chain ->

                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader(
                            "Content-Type",
                            "application/json; charset=utf-8"
                        )
                        .addHeader(
                            "Accept-Charset",
                            "utf-8"
                        )
                        .build()

                chain.proceed(request)
            }
            .build()

    val instance: ApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ApiService::class.java)
    }
}