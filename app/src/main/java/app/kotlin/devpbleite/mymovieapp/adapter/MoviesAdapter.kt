package app.kotlin.devpbleite.mymovieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.kotlin.devpbleite.mymovieapp.R
import app.kotlin.devpbleite.mymovieapp.databinding.ItemRowBinding
import app.kotlin.devpbleite.mymovieapp.response.MovieListResponse
import app.kotlin.devpbleite.mymovieapp.utils.Constants.POSTER_BASE_URL
import coil.load
import coil.size.Scale

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
  
  private lateinit var binding: ItemRowBinding
  private lateinit var context: Context
  
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    binding = ItemRowBinding.inflate(inflater, parent, false)
    context = parent.context
    return ViewHolder()
  }
  
  override fun getItemCount(): Int {
    return differ.currentList.size
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(differ.currentList[position])
  }
  
  inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovieListResponse.Result) {
      binding.apply {
        tvMovieTitle.text = item.title
        tvRate.text = item.vote_average.toString()
        val moviePosterURL = POSTER_BASE_URL + item?.posterPath
        imgMovie.load(moviePosterURL) {
          crossfade(true)
          placeholder(R.drawable.poster_placeholder)
          scale(Scale.FILL)
        }
        tvLang.text = item.original_language
        tvMovieDateRelease.text = item.release_date
      }
    }
  }
  
  private val differCallback=object : DiffUtil.ItemCallback<MovieListResponse.Result>() {
    override fun areItemsTheSame(
      oldItem: MovieListResponse.Result,
      newItem: MovieListResponse.Result
    ): Boolean {
      return oldItem.id == newItem.id
    }
  
    override fun areContentsTheSame(
      oldItem: MovieListResponse.Result,
      newItem: MovieListResponse.Result
    ): Boolean {
      return oldItem == newItem
    }
  
  }
  
  val differ = AsyncListDiffer(this, differCallback)
  
}