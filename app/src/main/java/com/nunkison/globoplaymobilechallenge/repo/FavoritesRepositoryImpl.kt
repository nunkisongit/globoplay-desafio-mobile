package com.nunkison.globoplaymobilechallenge.repo

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nunkison.globoplaymobilechallenge.R
import com.nunkison.globoplaymobilechallenge.project.structure.FavoritesRepository
import com.nunkison.globoplaymobilechallenge.project.structure.MoviesGroup
import com.nunkison.globoplaymobilechallenge.stringResource
import com.nunkison.globoplaymobilechallenge.ui.movies.data.MovieCover

class FavoritesRepositoryImpl(
    private val prefs: SharedPreferences
) : FavoritesRepository {

    override suspend fun add(movieCover: MovieCover) {
        edit {
            it.add(movieCover)
        }
    }

    override suspend fun remove(movieCover: MovieCover) {
        edit {
            it.remove(movieCover)
        }
    }

    override suspend fun getAll() = arrayListOf(
        MoviesGroup(
            category = stringResource(R.string.favorites),
            movieCovers = ArrayList(
                getOnSharedPrefs()
            )
        )
    )

    override suspend fun foundInFavorites(movieId: String) = getOnSharedPrefs().any {
        it.id == movieId
    }

    private fun getOnSharedPrefs() = (Gson().fromJson<LinkedHashSet<MovieCover>>(
        prefs.getString(FAVORITES_SHARED_PREF_KEY, "[]"),
        object : TypeToken<LinkedHashSet<MovieCover>>() {}.type
    ) ?: linkedSetOf())

    private fun edit(edit: (favorites: MutableSet<MovieCover>) -> Unit) {
        prefs.edit().putString(
            FAVORITES_SHARED_PREF_KEY,
            Gson().toJson(
                getOnSharedPrefs().toMutableSet().also {
                    edit(it)
                }
            )
        ).apply()
    }

    companion object {
        const val FAVORITES_SHARED_PREF_KEY = "FAVORITES_SHARED_PREF_KEY"
    }
}


