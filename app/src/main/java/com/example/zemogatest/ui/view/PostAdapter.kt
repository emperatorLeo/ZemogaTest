package com.example.zemogatest.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemogatest.R
import com.example.zemogatest.core.OnItemClickListener
import com.example.zemogatest.core.Post
import com.example.zemogatest.databinding.PostItemBinding

class PostAdapter(
    private val mProducts: List<Post>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PostAdapter.PostHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        with(holder) {
            with(mProducts[position]) {
                binding.postTitle.text = title
                assignStar(binding, isFavorite)
                binding.favoritePost.setOnClickListener {
                    isFavorite = !isFavorite
                    assignStar(binding, isFavorite)
                }
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClickListener(mProducts[position])
        }
    }

    private fun assignStar(binding: PostItemBinding, isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoritePost.setImageResource(R.mipmap.ic_favorite)
        } else {
            binding.favoritePost.setImageResource(R.mipmap.ic_not_favorite)
        }
    }

    override fun getItemCount() = mProducts.size

    inner class PostHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun addData(products: List<Post>) {
        mProducts.toMutableList().addAll(products)
    }

}