package com.webapplication.expressjwt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.webapplication.expressjwt.api.ApiService
import com.webapplication.expressjwt.api.ResponseModel
import com.webapplication.expressjwt.api.RetrofitInstance
import com.webapplication.expressjwt.databinding.ActivityLoginBinding
import com.webapplication.expressjwt.model.LoginModel
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityLoginBinding
    // Retrofit instance
    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@LoginActivity,R.layout.activity_login)

        mBinding.btnLogin.setOnClickListener {
            val loginModel = LoginModel(mBinding.edtUsername.text.toString(),mBinding.edtPassword.text.toString())
            apiService.login(loginModel).enqueue(object: Callback<ResponseModel>{
                override fun onResponse(
                    call: Call<ResponseModel>,
                    responseModel: retrofit2.Response<ResponseModel>
                ) {
                    if(responseModel.isSuccessful){
                        val apiResponse = responseModel.body()
                        val token = apiResponse?.token

                        val intent= Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("TOKEN",token)

                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.i("REPONSE","ERROR")
                }

            })
        }

        mBinding.txtRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

        }
    }
}