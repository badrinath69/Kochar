package com.pivotalsoft.kochar.api

import androidx.annotation.CheckResult
import com.pivotalsoft.kochar.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("signup")
    fun signupUser(

        @Field("fullname") fullname:String,
        @Field("mobile") mobile: String,
        @Field("password") password: String

    ): Call<SignupResponse>




    @FormUrlEncoded
    @POST("signin")
    fun userLogin(
        @Field("mobile") mobile:String,
        @Field("password") password: String
    ):Call<LoginResponse>



    @POST("schoolinfodisplay")
    fun getSchoolDetails() : Call<SchoolDataResponse>

    @FormUrlEncoded
    @POST("productdisplay")
    fun getProductDetails(
        @Field("fullname") fullname: String,
        @Field("subcatid") subcatid: Int,
        @Field("sxcode") sxcode: Int
    ):Call<ProductDetailsResponse>

    @FormUrlEncoded
    @POST("address")
    fun addAddress(
        @Field("userid") userid: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("landmark") landmark: String,
        @Field("state") state: String
    ): Call<AddAddressResponse>

    @FormUrlEncoded
    @POST("addressdisplay")
    fun getAddress(
        @Field("userid") userid: String
    ): Call<GetAddressResponse>
    @FormUrlEncoded
    @POST("cartinfo")
    fun addToCart(
        @Field("product_id") product_id: String,
        @Field("no_units") no_units: String,
        @Field("user_id") user_id: String,
        @Field("added_on") added_on: String,
        @Field("total_price") total_price: String,
        @Field("actual_price") actual_price:String
    ): Call<AddToCartResponse>

    @FormUrlEncoded
    @POST("listcart")
    fun getCartDetails(
        @Field("user_id") user_id:String
    ): Call<GetCartDetailsResponse>

    @FormUrlEncoded
    @POST("deleteaddress")
    fun deleteAddress(
        @Field("userid") userid: String,
        @Field("address_id") address_id: String
    ): Call<DeleteAddressResponse>

    @FormUrlEncoded
    @POST("cartitemdelete")
    fun deleteCartItems(
        @Field("cartitemid") cartitemid: String,
        @Field("user_id") user_id: String
    ): Call<DeleteCartResponse>

    @FormUrlEncoded
    @POST("modifycart")
    fun updateCartItems(
        @Field("cartitemid") cartitemid: String,
        @Field("actual_price") actual_price: String,
        @Field("no_units") no_units: String,
        @Field("total_price") total_price: String,
        @Field("added_on") added_on: String
    ): Call<updateCartItemsResponse>

    @FormUrlEncoded
    @POST("orderinfo")
    fun uploadcgstall(
        @Field("userid") userid: String,
        @Field("orderdate") orderdate: String,
        @Field("total_amount") total_amount: String,
        @Field("cgst") cgst: String,
        @Field("sgst") sgst: String,
        @Field("deliverycharges") deliverycharges: String,
        @Field("couponcode") couponcode: String,
        @Field("discount") discount: String,
        ): Call<uploadcgstallResponse>

}