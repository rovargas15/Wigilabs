/*
 * *
 *  * Created by Rafael Vargas on 6/10/22, 2:40 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *
 */

package com.example.wigilabs.ui.main.detail.state

import com.example.domain.model.Movie

sealed interface DetailMovieState {
    object Loader : DetailMovieState
    object Error : DetailMovieState
    data class Success(val movie: Movie) : DetailMovieState
}
