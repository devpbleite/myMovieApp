package app.kotlin.devpbleite.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.kotlin.devpbleite.mymovieapp.adapter.MoviesAdapter
import app.kotlin.devpbleite.mymovieapp.api.ApiClient
import app.kotlin.devpbleite.mymovieapp.api.ApiService
import app.kotlin.devpbleite.mymovieapp.databinding.ActivityMainBinding
import app.kotlin.devpbleite.mymovieapp.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
  
  private lateinit var binding: ActivityMainBinding
  private val movieAdapter by lazy { MoviesAdapter() }
  private val api : ApiService by lazy {
    ApiClient().getClient().create(ApiService::class.java)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    
    binding.apply {
      progressBar.visibility = View.VISIBLE
      
      val callMovieApi = api.getPopularMovies(1)
      callMovieApi.enqueue(object : Callback<MovieListResponse>{
        override fun onResponse(
          call: Call<MovieListResponse>,
          response: Response<MovieListResponse>
        ) {
          TODO("Not yet implemented")
        }
  
        override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
          TODO("Not yet implemented")
        }
  
      })
    }
    
  }
}