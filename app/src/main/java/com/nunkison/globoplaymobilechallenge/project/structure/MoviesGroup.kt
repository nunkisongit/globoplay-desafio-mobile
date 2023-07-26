package com.nunkison.globoplaymobilechallenge.project.structure

import com.nunkison.globoplaymobilechallenge.ui.movies.data.MovieCover

data class MoviesGroup(
    val category: String,
    val movieCovers: List<MovieCover>
)