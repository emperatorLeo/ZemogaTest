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
import com.example.zemogatest.core.ComplementaryInfo
import com.example.zemogatest.databinding.FragmentPostDetailsBinding
import com.example.zemogatest.ui.viewmodel.SharedViewModel

class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        requestComplementaryInfo()
        setUpListener()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonDetails.setOnClickListener {
            findNavController().navigate(R.id.action_post_details_to_post_list)
        }
    }

    private fun requestComplementaryInfo() {
        sharedViewModel.getUserInfoAndComments()
    }

    private fun setUpListener(){
        sharedViewModel.complementaryInfo.observe(requireActivity()) {
            feedingView(it)
        }
    }

    private fun feedingView(info: ComplementaryInfo){
        val detailsBinding = binding.postDetails

        val post = sharedViewModel.post.value!!
        detailsBinding.tvTitle.text = post.title
        detailsBinding.tvDescription.text = post.post

        val user = info.user!!
        detailsBinding.tvNameAuthor.text = user.name
        detailsBinding.tvUsernameAuthor.text = user.username
        detailsBinding.tvEmailAuthor.text = user.email

        val comments = info.commentList
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CommentAdapter(comments)
        }
    }
}