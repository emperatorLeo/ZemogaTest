package com.example.zemogatest.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemogatest.core.Comment
import com.example.zemogatest.databinding.CommentItemBinding

class CommentAdapter(
    private val mProducts: List<Comment>
) : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        with(holder) {
            with(mProducts[position]) {
                binding.tvComment.text = this.comment
            }
        }
    }

    override fun getItemCount() = mProducts.size

    inner class CommentHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

}