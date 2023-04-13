package com.example.zemogatest.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemogatest.R
import com.example.zemogatest.core.OnItemClickListener
import com.example.zemogatest.core.Post
import com.example.zemogatest.databinding.FragmentPostListBinding
import com.example.zemogatest.ui.UiState
import com.example.zemogatest.ui.viewmodel.SharedViewModel
import java.util.LinkedList

class PostListFragment : Fragment() {

    private var _binding: FragmentPostListBinding? = null
    private lateinit var postAdapter: PostAdapter
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        sharedViewModel.getPosts()

        listeners()
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun listeners() {
        sharedViewModel.uiState.observe(requireActivity()) {
            when (it) {
                is UiState.Success<*> -> {
                    if (it.data is LinkedList<*>) {
                        onSuccess(it.data as LinkedList<Post>)
                    }
                }
                is UiState.Loading -> {
                    loaderShouldBeShow(it.isVisible)
                }
                is UiState.Error -> {
                    onError()
                }
            }
        }
        binding.btnDeletePosts.setOnClickListener {
            onDeleteAllPost(it)
        }

        binding.btnReloadPosts.setOnClickListener {
            onReloadPost(it)
        }
    }

    private fun onError() {
        Toast.makeText(
            requireContext(),
            "there were an error calling the api",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loaderShouldBeShow(should: Boolean) {
        if (should)
            binding.loader.visibility = View.VISIBLE
        else binding.loader.visibility = View.INVISIBLE
    }

    private fun onDeleteAllPost(view: View) {
        sharedViewModel.deleteAllPost()
        postAdapter.updateList(sharedViewModel.postList.value!!)
        postAdapter.notifyDataSetChanged()
        view.visibility = View.INVISIBLE
        binding.btnReloadPosts.visibility = View.VISIBLE
    }

    private fun onReloadPost(view: View) {
        sharedViewModel.getPosts()
        postAdapter.notifyDataSetChanged()
        view.visibility = View.INVISIBLE
        binding.btnDeletePosts.visibility = View.VISIBLE
    }

    private fun onSuccess(list: LinkedList<Post>) {
        postAdapter =
            PostAdapter(list, object : OnItemClickListener {
                override fun onItemClickListener(post: Post) {
                    sharedViewModel.addingPost(post)
                    findNavController().navigate(R.id.action_post_list_to_post_details)
                }
            })
        binding.loader.visibility = View.GONE
        binding.rvPostList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
            visibility = View.VISIBLE
        }
    }

}