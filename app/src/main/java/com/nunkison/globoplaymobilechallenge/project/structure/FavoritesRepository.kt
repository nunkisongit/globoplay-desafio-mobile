package com.nunkison.globoplaymobilechallenge.project.structure

import com.nunkison.globoplaymobilechallenge.ui.movies.data.MovieCover

interface FavoritesRepository {
    suspend fun add(movieCover: MovieCover)
    suspend fun remove(movieCover: MovieCover)
    suspend fun getAll(): List<MoviesGroup>
    suspend fun foundInFavorites(movieId: String): Boolean
}