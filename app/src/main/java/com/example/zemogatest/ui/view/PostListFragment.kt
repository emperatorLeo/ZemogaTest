package com.example.zemogatest.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemogatest.R
import com.example.zemogatest.core.OnItemClickListener
import com.example.zemogatest.core.Post
import com.example.zemogatest.databinding.FragmentPostListBinding
import com.example.zemogatest.ui.viewmodel.MainViewModel

class PostListFragment : Fragment() {

    private var _binding: FragmentPostListBinding? = null
    private lateinit var postAdapter: PostAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val sharedViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        sharedViewModel.testingApi()

        initViews()
        return binding.root
    }

    private fun initViews(){
        sharedViewModel.postList.observe(requireActivity()) {
            postAdapter = PostAdapter(it, object : OnItemClickListener {
                override fun onItemClickListener(post: Post) {
                    findNavController().navigate(R.id.action_post_list_to_post_details)
                }
            })
            binding.rvPostList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = postAdapter
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}