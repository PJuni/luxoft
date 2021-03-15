package com.backbase.assignment

import com.backbase.assignment.api.GenreAPI
import com.backbase.assignment.api.response.ApiResult
import com.backbase.assignment.data.genres.GenreRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.json.JsonObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GenreRepositoryTest {

    @MockK
    lateinit var api: GenreAPI

    private lateinit var dataSource: GenreRepositoryImpl

    @Before

    fun setup() {
        MockKAnnotations.init(this)

        dataSource = GenreRepositoryImpl(
            api
        )
    }

    @Test
    fun `run getGenres`() = runBlockingTest {
        val dataResponse = JsonObject(mapOf())
        coEvery {
            api.getGenres()
        } returns Response.success(dataResponse)

        val result = dataSource.getGenres()

        coVerify { api.getGenres() }
        Assert.assertEquals(dataResponse, (result as ApiResult.Success).data)
    }
}