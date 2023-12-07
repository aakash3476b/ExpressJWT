package com.webapplication.expressjwt.api

import com.webapplication.expressjwt.model.BooksModel
import com.webapplication.expressjwt.model.LoginModel
import com.webapplication.expressjwt.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Response


interface ApiService {

    @POST("login")
    fun login(@Body loginModel: LoginModel): Call<ResponseModel>


    @GET("book")
    suspend fun books(@Header("Authorization") token:String): Response<List<BooksModel>>

    @DELETE("book/{bookID}")
    suspend fun delete(@Header("Authorization") token:String,@Path("bookID") bookID: String): Response<Unit>

    @POST("register")
    fun register(@Body registerModel: RegisterModel): Call<ResponseModel>

}