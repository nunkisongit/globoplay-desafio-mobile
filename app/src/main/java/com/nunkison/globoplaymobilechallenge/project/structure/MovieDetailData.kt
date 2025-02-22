package com.nunkison.globoplaymobilechallenge.project.structure

import com.nunkison.globoplaymobilechallenge.ui.movies.data.MovieCover

data class MovieDetailData(
    val id: String,
    val name: String,
    val coverPath: String?,
    val category: String,
    val description: String,
    val isFavorite: Boolean,
    val year: String,
    val country: String,
    val producer: String,
    val youtubeKey: String,
    val relatedMovies: List<MovieCover>,
    val revenue: Int,
    val runtime: Int,
    val vote_average: Double,
    val budget: Int
)