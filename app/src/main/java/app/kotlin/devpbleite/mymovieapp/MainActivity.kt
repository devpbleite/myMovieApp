package app.kotlin.devpbleite.mymovieapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.kotlin.devpbleite.mymovieapp.adapter.MoviesAdapter
import app.kotlin.devpbleite.mymovieapp.api.ApiClient
import app.kotlin.devpbleite.mymovieapp.api.ApiService
import app.kotlin.devpbleite.mymovieapp.databinding.ActivityMainBinding
import app.kotlin.devpbleite.mymovieapp.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
          progressBar.visibility = View.GONE
          when(response.code()) {
            // Suceessful response
            in 200..299 -> {
              response.body().let { itBody ->
                itBody?.results.let { itData ->
                  if(itData!!.isNotEmpty()) {
                    movieAdapter.differ.submitList(itData)
                    rvMovie.apply {
                      layoutManager = LinearLayoutManager(this@MainActivity)
                      adapter = movieAdapter
                    }
                  }
                }
                
              }
            }
            // Redirection response
            in 300..399 -> {
              Log.d("Response Code", " Redirection response : ${response.code()}")
            }
            // Client error response
            in 400..499 -> {
              Log.d("Response Code", " Client error response : ${response.code()}")
            }
            // Server error response
            in 500..599 -> {
              Log.d("Response Code", " Server error response : ${response.code()}")
            }
          }
        }
        
        override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
          binding.progressBar.visibility = View.GONE
          Log.e("onFailure", "Error : ${t.message}")
        }
        
      })
    }
    
  }
}