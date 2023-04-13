package com.example.zemogatest.domain

import com.example.zemogatest.core.User
import com.example.zemogatest.data.JsonHolderRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

internal class GetUserInfoUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: JsonHolderRepository

    lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUserInfoUseCase = GetUserInfoUseCase(repository)
    }

    @Test
    fun `when invoke of getUserInfo is called it should call to getUserInfo method of the repository`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getUserInfo(0) } returns Response.success(User("","",""))

            //WHEN
            getUserInfoUseCase.invoke(0)

            //Then
            coVerify(exactly = 1) { repository.getUserInfo(0) }
        }
}