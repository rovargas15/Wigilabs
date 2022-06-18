package com.example.wigilabs.ui.main.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Movie
import com.example.domain.uc.GetMovieByIdUC
import com.example.wigilabs.ui.main.detail.state.DetailMovieState
import com.example.wigilabs.ui.main.navigation.Parameter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieByIdUC: GetMovieByIdUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableLiveData<DetailMovieState>()
    val viewState: LiveData<DetailMovieState>
        get() = _viewState

    init {
        savedStateHandle.get<Long>(Parameter.MOVIE_ID)?.let {
            getMovieById(it)
        }
    }

    private fun getMovieById(movieId: Long) {
        getMovieByIdUC.invoke(movieId).map { result ->
            result.fold(
                onSuccess = { movie: Movie ->
                    _viewState.postValue(DetailMovieState.Success(movie))
                },
                onFailure = {
                    _viewState.postValue(DetailMovieState.Error)
                }
            )
        }.onStart {
            _viewState.postValue(DetailMovieState.Loader)
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
