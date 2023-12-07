package com.webapplication.expressjwt.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webapplication.expressjwt.R
import com.webapplication.expressjwt.api.ApiService
import com.webapplication.expressjwt.api.RetrofitInstance
import com.webapplication.expressjwt.model.BooksModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class BooksAdapter(
    var context: Context,
    var booksList: ArrayList<BooksModel>,
    var token: String?
) : RecyclerView.Adapter<BooksAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_books_adapter, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val book = booksList[position]

        holder.bookNameTextView.text = book.BooksName
        holder.ratingTextView.text = book.Rating.toString()
        holder.authorTextView.text = book.Author
        holder.genreTextView.text = book.Genre
        holder.deleteButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

                try {
                    val deleteApi = apiService.delete(token!!, book.BookID.toString())
                    Log.i("RESPONSE",deleteApi.body().toString())
                    if(deleteApi.isSuccessful){
                        GlobalScope.launch(Dispatchers.Main) {
                            booksList.removeAt(position)
                            notifyDataSetChanged()
                        }

                    }
                } catch (e: Exception) {
                    // Handle exception
                }
            }

        }

    }

    open fun onClick(_id: Int, position: Int) {

    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookNameTextView: TextView = itemView.findViewById(R.id.txtbookName)
        val ratingTextView: TextView = itemView.findViewById(R.id.txtRating)
        val authorTextView: TextView = itemView.findViewById(R.id.txtAuthor)
        val genreTextView: TextView = itemView.findViewById(R.id.txtGenre)
        val deleteButton: Button = itemView.findViewById(R.id.btnDelete)
    }
}

