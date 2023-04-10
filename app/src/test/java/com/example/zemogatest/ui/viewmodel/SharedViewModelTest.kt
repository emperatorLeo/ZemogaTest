package com.example.zemogatest.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.zemogatest.core.Comment
import com.example.zemogatest.core.Post
import com.example.zemogatest.core.User
import com.example.zemogatest.domain.GetAllPostsUseCase
import com.example.zemogatest.domain.GetCommentsUseCase
import com.example.zemogatest.domain.GetUserInfoUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import java.util.LinkedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SharedViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getAllPostsUseCase: GetAllPostsUseCase

    @RelaxedMockK
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @RelaxedMockK
    private lateinit var getCommentsUseCase: GetCommentsUseCase

    private lateinit var sharedViewModel: SharedViewModel


    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        sharedViewModel =
            SharedViewModel(getAllPostsUseCase, getUserInfoUseCase, getCommentsUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel calls getPosts , postListLiveData should have a list`() = runTest {
        //GIVEN
        val list = LinkedList(listOf(Post(0, 0, "title", "post"), Post(1, 1, "title", "post")))
        coEvery { getAllPostsUseCase.invoke() } returns list

        //WHEN
        sharedViewModel.getPosts()

        //THEN
        assert(sharedViewModel.postList.value == list)
    }

    @Test
    fun `when viewModel addingPost , postLiveData should have the post`() = runTest {
        //GIVEN
        val post = Post(0, 0, "title", "post")

        //WHEN
        sharedViewModel.addingPost(post)

        //THEN
        assert(sharedViewModel.post.value == post)
    }

    @Test
    fun `when viewModel delete all post , postListLiveData should remain the favorites ones`() =
        runTest {
            //GIVEN
            val post1 = Post(0, 0, "title", "post")
            val post2 = Post(1, 1, "title", "post")
            post2.isFavorite = true
            val post3 = Post(2, 2, "title", "post")
            val post4 = Post(3, 3, "title", "post")
            post4.isFavorite = true

            val list = LinkedList(listOf(post1, post2, post3, post4))
            coEvery { getAllPostsUseCase.invoke() } returns list

            //WHEN
            sharedViewModel.getPosts()
            sharedViewModel.deleteAllPost()

            //THEN
            assert(sharedViewModel.postList.value?.size == 2)
            assert(sharedViewModel.postList.value == LinkedList(listOf(post2, post4)))
        }

    @Test
    fun `when viewModel getUserInfoAndComments, complementaryInfoLiveData should have an user and a list of comments`() =
        runTest {
          //GIVEN
            val post = Post(0, 0, "title", "post")
            val user = User("","","")
            val listOfComments = listOf(Comment("",""))

            coEvery { getUserInfoUseCase.invoke(post.userId) } returns user
            coEvery { getCommentsUseCase.invoke(post.id) } returns listOfComments

            //WHEN
            sharedViewModel.addingPost(post)
            sharedViewModel.getUserInfoAndComments()

            //THEN
            assert(sharedViewModel.complementaryInfo.value?.user == user)
            assert(sharedViewModel.complementaryInfo.value?.commentList == listOfComments)
        }

}