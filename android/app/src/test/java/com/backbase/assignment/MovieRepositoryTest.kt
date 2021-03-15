package com.backbase.assignment

import com.backbase.assignment.api.MoviesAPI
import com.backbase.assignment.api.response.ApiResult
import com.backbase.assignment.data.movies.MoviesRepositoryImpl
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

class MovieRepositoryTest {

    @MockK
    lateinit var api: MoviesAPI

    private lateinit var dataSource: MoviesRepositoryImpl

    @Before

    fun setup() {
        MockKAnnotations.init(this)

        dataSource = MoviesRepositoryImpl(
            api
        )
    }

    @Test
    fun `run getNowPlayingMovies`() = runBlockingTest {
        val dataResponse = JsonObject(mapOf())
        coEvery {
            api.getNowPlayingMovies(1)
        } returns Response.success(dataResponse)

        val result = dataSource.getPlayingNowMovies(1)

        coVerify { api.getNowPlayingMovies(1) }
        Assert.assertEquals(dataResponse, (result as ApiResult.Success).data)
    }

    @Test
    fun `run getPopularMovies`() = runBlockingTest {
        val dataResponse = JsonObject(mapOf())
        coEvery {
            api.getPopularMovies(1)
        } returns Response.success(dataResponse)

        val result = dataSource.getMostPopularMovies(1)

        coVerify { api.getPopularMovies(1) }
        Assert.assertEquals(dataResponse, (result as ApiResult.Success).data)
    }
}