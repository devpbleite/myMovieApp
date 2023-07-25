package app.kotlin.devpbleite.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.kotlin.devpbleite.mymovieapp.api.ApiClient
import app.kotlin.devpbleite.mymovieapp.api.ApiService
import app.kotlin.devpbleite.mymovieapp.databinding.ActivityDetailsMovieBinding
import app.kotlin.devpbleite.mymovieapp.response.DetailsMovieResponse
import app.kotlin.devpbleite.mymovieapp.utils.Constants.POSTER_BASE_URL
import coil.load
import coil.size.Scale
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class DetailsMovieActivity : AppCompatActivity() {
  
  private lateinit var binding: ActivityDetailsMovieBinding
  private val api : ApiService by lazy {
    ApiClient().getClient().create(ApiService::class.java)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val movieId = intent.getIntExtra("id", 1)
    binding.apply {
      val callDetailsMovieApi = api.getMovieDetails(movieId)
      callDetailsMovieApi.enqueue(object : Callback<DetailsMovieResponse>{
        override fun onResponse(
          call: Call<DetailsMovieResponse>,
          response: Response<DetailsMovieResponse>
        ) {
          when(response.code()) {
            in 200..299 -> {
              response.body().let { itBody->
                val imagePoster = POSTER_BASE_URL + itBody!!.poster_path
                imgMovie.load(imagePoster){
                  crossfade(true)
                  placeholder(R.drawable.poster_placeholder)
                  scale(Scale.FILL)
                }
                imgMovieBack.load(imagePoster){
                  crossfade(true)
                  placeholder(R.drawable.poster_placeholder)
                  scale(Scale.FILL)
                }
                tvMovieTitle.text = itBody.title
                tvTagLline.text = itBody.tagline
                tvMovieDateRelease.text = itBody.release_date
                tvMovieRating.text = itBody.vote_average.toString()
                tvMovieRuntime.text = itBody.runtime.toString()
                tvMovieBudget.text = itBody.budget.toString()
                tvMovieRevenue.text = itBody.revenue.toString()
                tvMovieOverview.text = itBody.overview
              }
            }
            in 300..399 -> {
              Log.d("Response Code", " Redirection response : ${response.code()}")
            }
            
            in 400..499 -> {
              Log.d("Response Code", " Client error response : ${response.code()}")
            }
            
            in 500..599 -> {
              Log.d("Response Code", " Server error response : ${response.code()}")
            }
          }
        }
  
        override fun onFailure(call: Call<DetailsMovieResponse>, t: Throwable) {
          Log.e("onFailure", "Error : ${t.message}")
        }
  
      })
    }
  }
}