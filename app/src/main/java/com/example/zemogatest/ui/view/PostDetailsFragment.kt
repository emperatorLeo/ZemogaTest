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
import com.example.zemogatest.core.ComplementaryInfo
import com.example.zemogatest.databinding.FragmentPostDetailsBinding
import com.example.zemogatest.ui.UiState
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

        binding.buttonPrevious.setOnClickListener {
            findNavController().navigate(R.id.action_post_details_to_post_list)
        }
    }

    private fun requestComplementaryInfo() {
        sharedViewModel.getUserInfoAndComments()
    }

    private fun setUpListener() {
        sharedViewModel.uiState.observe(requireActivity()) {
            when (it) {
                is UiState.Error -> {
                    onError()
                }
                is UiState.Loading -> {
                    showViews(it.isVisible)
                }
                is UiState.Success<*> -> {
                    if (it.data is ComplementaryInfo) {
                        feedingView(it.data)
                    }
                }
            }

        }
    }

    private fun feedingView(info: ComplementaryInfo) {
        val detailsBinding = _binding?.postDetails!!

        val post = sharedViewModel.post
        detailsBinding.tvTitle.text = post.title
        detailsBinding.tvDescription.text = post.post

        val user = info.user!!
        detailsBinding.tvNameAuthor.text = user.name
        detailsBinding.tvUsernameAuthor.text = user.username
        detailsBinding.tvEmailAuthor.text = user.email

        val comments = info.commentList
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = comments?.let { CommentAdapter(it) }
        }
    }

    private fun onError() {
        Toast.makeText(
            requireContext(),
            "there were an error calling the api",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showViews(visible: Boolean) {
        if (visible)
            showLoader()
        else hideLoader()
    }

    private fun hideLoader() {
        binding.apply {
            loader.visibility = View.INVISIBLE
            postDetails.apply {
                with(View.VISIBLE) {
                    tvLabelTitle.visibility = this
                    tvTitle.visibility = this
                    tvLabelDescription.visibility = this
                    tvDescription.visibility = this
                    tvLabelAuthor.visibility = this
                    tvLabelNameAuthor.visibility = this
                    tvNameAuthor.visibility = this
                    tvLabelUsernameAuthor.visibility = this
                    tvUsernameAuthor.visibility = this
                    tvLabelEmailAuthor.visibility = this
                    tvEmailAuthor.visibility = this
                    rvComments.visibility = this
                    divider.visibility = this
                    buttonPrevious.visibility = this
                }
            }
        }
    }

    private fun showLoader() {
        binding.apply {
            loader.visibility = View.VISIBLE
            postDetails.apply {
                with(View.INVISIBLE) {
                    tvLabelTitle.visibility = this
                    tvTitle.visibility = this
                    tvLabelDescription.visibility = this
                    tvDescription.visibility = this
                    tvLabelAuthor.visibility = this
                    tvLabelNameAuthor.visibility = this
                    tvNameAuthor.visibility = this
                    tvLabelUsernameAuthor.visibility = this
                    tvUsernameAuthor.visibility = this
                    tvLabelEmailAuthor.visibility = this
                    tvEmailAuthor.visibility = this
                    rvComments.visibility = this
                    divider.visibility = this
                    buttonPrevious.visibility = this
                }
            }
        }
    }

}