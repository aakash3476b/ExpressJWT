package com.webapplication.expressjwt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.webapplication.expressjwt.api.ApiService
import com.webapplication.expressjwt.api.ResponseModel
import com.webapplication.expressjwt.api.RetrofitInstance
import com.webapplication.expressjwt.databinding.ActivityRegisterBinding
import com.webapplication.expressjwt.model.RegisterModel
import retrofit2.Call
import retrofit2.Callback

class RegisterActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityRegisterBinding

    // Retrofit instance
    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@RegisterActivity, R.layout.activity_register)

        mBinding.btnRegister.setOnClickListener {
            val registerModel = RegisterModel(
                mBinding.edtUsername.text.toString(),
                mBinding.edtPassword.text.toString()
            )
            apiService.register(registerModel).enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    responseModel: retrofit2.Response<ResponseModel>
                ) {
                    if (responseModel.isSuccessful) {
                        val apiResponse = responseModel.body()
                        val token = apiResponse?.token

                        val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                        intent.putExtra("TOKEN", token)

                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.i("REPONSE", "ERROR")
                }

            })
        }
    }
}