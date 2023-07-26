package com.nunkison.globoplaymobilechallenge.project.structure

data class MoviesScreenSuccessState(
    val favoriteFilterEnable: Boolean,
    val data: List<MoviesGroup>
)