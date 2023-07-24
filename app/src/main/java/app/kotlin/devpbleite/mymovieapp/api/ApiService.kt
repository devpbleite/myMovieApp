package app.kotlin.devpbleite.mymovieapp.api

import app.kotlin.devpbleite.mymovieapp.response.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("movie/popular")
  fun getPopularMovies(@Query("page") page: Int) : Call<MovieListResponse>

}