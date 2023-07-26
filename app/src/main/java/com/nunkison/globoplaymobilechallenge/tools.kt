package com.nunkison.globoplaymobilechallenge

import com.nunkison.globoplaymobilechallenge.project.api.Genre
import com.nunkison.globoplaymobilechallenge.project.api.MovieListResponse
import com.nunkison.globoplaymobilechallenge.project.api.ProductionCompany
import com.nunkison.globoplaymobilechallenge.project.api.ProductionCountry
import com.nunkison.globoplaymobilechallenge.ui.movies.data.MovieCover
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun thumbImage(imgPath: String?): String? {
    imgPath?.let {
        return "${stringResource(R.string.base_thumb_image_url)}${it}"
    }
    return null
}

fun originalImage(imgPath: String?): String? {
    imgPath?.let {
        return "${stringResource(R.string.base_original_image_url)}${it}"
    }
    return null
}

fun getYear(date: String) = Calendar.getInstance().apply {
    time = try {
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)!!
    } catch (e: Exception) {
        Date()
    }
}.get(Calendar.YEAR).toString()

fun Int.toCurrency(symbol: String): String {
    val format = DecimalFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    symbols.currencySymbol = symbol
    format.decimalFormatSymbols = symbols
    return format.format(this)
}

fun List<MovieListResponse.DiscoverMovie>.toMovieCover() = mapTo(arrayListOf()) {
    MovieCover(
        id = it.id,
        name = it.title,
        cover = thumbImage(it.poster_path)
    )
}.filter {
    it.cover != null
}

fun List<Genre>.toCommaString() = mapTo(arrayListOf()) {
    it.name
}.joinToString(", ")

fun List<ProductionCountry>.toCommaString() = mapTo(arrayListOf()) {
    it.name
}.joinToString(", ")

fun List<ProductionCompany>.toCommaString() = mapTo(arrayListOf()) {
    it.name
}.joinToString(", ")

