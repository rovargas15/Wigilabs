package com.example.wigilabs.ui.main.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Movie
import com.example.domain.uc.GetMovieUC
import com.example.wigilabs.ui.main.movie.intent.MovieEvent
import com.example.wigilabs.ui.main.movie.state.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUC: GetMovieUC,
    private val coroutineDispatcher: CoroutineDispatcher

) : ViewModel() {

    private val _viewState = MutableLiveData<MovieState>()
    val viewState: LiveData<MovieState>
        get() = _viewState

    init {
        process(MovieEvent.GetMovieALl)
    }

    fun process(event: MovieEvent) {
        when (event) {
            is MovieEvent.GetMovieALl -> {
                getMovie()
            }
            is MovieEvent.Reload -> {
                getMovie()
            }
        }
    }

    private fun getMovie() {
        getMovieUC.invoke().map { result: Result<List<Movie>> ->
            result.fold(
                onSuccess = { users: List<Movie> ->
                    _viewState.postValue(MovieState.Success(users))
                },
                onFailure = {
                    _viewState.postValue(MovieState.Error)
                }
            )
        }.onStart {
            _viewState.postValue(MovieState.Loader)
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
