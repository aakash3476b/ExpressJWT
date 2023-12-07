package com.webapplication.expressjwt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.webapplication.expressjwt.adapter.BooksAdapter
import com.webapplication.expressjwt.api.ApiService
import com.webapplication.expressjwt.api.ResponseMessage
import com.webapplication.expressjwt.api.RetrofitInstance
import com.webapplication.expressjwt.databinding.ActivityHomeBinding
import com.webapplication.expressjwt.model.BooksModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback

class HomeActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityHomeBinding
    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)
    private lateinit var adapter: BooksAdapter
    var booksList = ArrayList<BooksModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val token = intent.getStringExtra("TOKEN")

        getBooks(token)
    }

    fun getBooks(token:String?){
        GlobalScope.launch(Dispatchers.Main) {
            if (token != null) {
                val response = apiService.books(token)
                withContext(Dispatchers.Main) {
                    booksList = response.body() as ArrayList<BooksModel>
                    adapter = BooksAdapter(this@HomeActivity, booksList,token)
                    mBinding.rvBooks.adapter = adapter
                }
            }

        }
    }
}