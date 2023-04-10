package com.example.zemogatest.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemogatest.R
import com.example.zemogatest.core.OnItemClickListener
import com.example.zemogatest.core.Post
import com.example.zemogatest.databinding.PostItemBinding
import java.util.LinkedList

class PostAdapter(
    private var mProducts: LinkedList<Post>,
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
                binding.apply {
                    postTitle.text = title
                    assignStar(this, isFavorite)
                }
                binding.favoritePost.setOnClickListener {
                    isFavorite = !isFavorite
                    assignStar(binding, isFavorite)
                    changePosition(isFavorite, this, position)
                }
                binding.deletePost.setOnClickListener {
                    deletePost(this, position)
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

    private fun changePosition(isFavorite: Boolean, post: Post, position: Int) {
        if (isFavorite) {
            deletePost(post, position)
            mProducts.addFirst(post)
        } else {
            deletePost(post, position)
            mProducts.addLast(post)
        }
        notifyDataSetChanged()
    }

    private fun deletePost(post: Post, position: Int) {
        mProducts.remove(post)
        notifyItemChanged(position)
    }

    override fun getItemCount() = mProducts.size

    inner class PostHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

}