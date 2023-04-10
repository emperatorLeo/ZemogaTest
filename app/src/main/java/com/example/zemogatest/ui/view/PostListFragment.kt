package com.example.zemogatest.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemogatest.R
import com.example.zemogatest.core.OnItemClickListener
import com.example.zemogatest.core.Post
import com.example.zemogatest.databinding.FragmentPostListBinding
import com.example.zemogatest.ui.viewmodel.SharedViewModel

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

        initViews()
        listeners()
        return binding.root
    }

    private fun initViews() {
        sharedViewModel.postList.observe(requireActivity()) {
            postAdapter = PostAdapter(it, object : OnItemClickListener {
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

    private fun listeners(){
        binding.btnDeletePosts.setOnClickListener {
            sharedViewModel.deleteAllPost()
            postAdapter.updateList(sharedViewModel.postList.value!!)
            postAdapter.notifyDataSetChanged()
            it.visibility = View.INVISIBLE
            binding.btnReloadPosts.visibility = View.VISIBLE
        }

        binding.btnReloadPosts.setOnClickListener {
            sharedViewModel.getPosts()
            postAdapter.notifyDataSetChanged()
            it.visibility = View.INVISIBLE
            binding.btnDeletePosts.visibility = View.VISIBLE
        }
    }
}