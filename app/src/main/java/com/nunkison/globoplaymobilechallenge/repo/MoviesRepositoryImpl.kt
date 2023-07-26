package com.nunkison.globoplaymobilechallenge.repo

import com.nunkison.globoplaymobilechallenge.R
import com.nunkison.globoplaymobilechallenge.getYear
import com.nunkison.globoplaymobilechallenge.project.api.Genre
import com.nunkison.globoplaymobilechallenge.project.api.TmdbService
import com.nunkison.globoplaymobilechallenge.project.structure.FavoritesRepository
import com.nunkison.globoplaymobilechallenge.project.structure.MovieDetailData
import com.nunkison.globoplaymobilechallenge.project.structure.MoviesGroup
import com.nunkison.globoplaymobilechallenge.project.structure.MoviesRepository
import com.nunkison.globoplaymobilechallenge.stringResource
import com.nunkison.globoplaymobilechallenge.thumbImage
import com.nunkison.globoplaymobilechallenge.toCommaString
import com.nunkison.globoplaymobilechallenge.toMovieCover
import com.nunkison.globoplaymobilechallenge.ui.movies.data.MovieCover

class MoviesRepositoryImpl(
    private val service: TmdbService,
    private val favoritesRepository: FavoritesRepository
) : MoviesRepository {

    override suspend fun getDiscoverMovies(): List<MoviesGroup> =
        service.genreList().body()?.genres?.mapTo(arrayListOf()) {
            MoviesGroup(
                category = it.name,
                movieCovers = service.discoverMovie(it.id).body()?.results?.toMovieCover()
                    ?: arrayListOf()
            )
        } ?: arrayListOf()

    override suspend fun getMovie(id: String) = service.movie(id).body()?.let {
        MovieDetailData(
            id = it.id,
            name = it.original_title,
            coverPath = thumbImage(it.poster_path),
            category = it.genres.toCommaString(),
            description = it.overview,
            isFavorite = favoritesRepository.foundInFavorites(it.id),
            year = getYear(it.release_date),
            country = it.production_countries.toCommaString(),
            producer = it.production_companies.toCommaString(),
            youtubeKey = getYoutubeKey(it.id) ?: "",
            relatedMovies = getRelatedMovies(it.genres),
            revenue = it.revenue,
            vote_average = it.vote_average,
            runtime = it.runtime,
            budget = it.budget
        )
    }

    override suspend fun getRelatedMovies(genres: List<Genre>) = arrayListOf<MovieCover>().apply {
        genres.map { genre ->
            addAll(
                service.discoverMovie(genre.id).body()?.results?.toMovieCover()
                    ?: arrayListOf()
            )
        }
    }

    override suspend fun getYoutubeKey(id: String) =
        service.movieVideos(id).body()?.results?.firstOrNull {
            it.site == "YouTube"
        }?.key

    override suspend fun searchVideos(query: String) = arrayListOf(
        MoviesGroup(
            category = "${stringResource(R.string.search_for)}: $query",
            movieCovers = service.searchMovie(query).body()?.results?.toMovieCover()
                ?: arrayListOf(),
        )
    )
}


