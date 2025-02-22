package com.nunkison.globoplaymobilechallenge.ui.movie_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nunkison.globoplaymobilechallenge.R
import com.nunkison.globoplaymobilechallenge.originalImage
import com.nunkison.globoplaymobilechallenge.project.structure.MovieDetailData
import com.nunkison.globoplaymobilechallenge.toCurrency

@Composable
fun MovieDetailLayout(
    data: MovieDetailData,
    onMovieClick: (id: String) -> Unit,
    onWatchClick: (youtubeKey: String) -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val selectedTabIndex = remember { mutableStateOf(0) }
    val showDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 56.dp)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .alpha(0.25f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.coverPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.BottomStart)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = 0.0f,
                                    endY = 500.0f
                                )
                            )
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.Black)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(150.dp)
                            .clickable {
                                showDialog.value = true
                            },
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.coverPath)
                            .crossfade(true)
                            .build(),
                        contentDescription = data.name,
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    text = data.name,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    text = data.category,
                    fontSize = 12.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    text = data.description,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
            }
        }
        Box(
            modifier = Modifier.background(Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10),
                    onClick = {
                        onWatchClick(data.youtubeKey)
                    }
                ) {
                    Icon(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Assista",
                        tint = Color.DarkGray
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = "Assista",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(10),
                    onClick = {
                        onFavoriteClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        imageVector = if (data.isFavorite) Icons.Filled.Check else Icons.Filled.Star,
                        contentDescription = if (data.isFavorite) stringResource(R.string.unfavorite) else stringResource(
                            R.string.favorite
                        ),
                        tint = Color.White
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = if (data.isFavorite) stringResource(R.string.added) else stringResource(
                            R.string.my_list
                        ),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }

            }
        }
        TabRow(selectedTabIndex = selectedTabIndex.value,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color.White,
                    modifier = Modifier.tabIndicatorOffset(
                        currentTabPosition = tabPositions[selectedTabIndex.value],
                    ),
                )
            },
            divider = {}
        ) {
            listOf(
                stringResource(R.string.similar).uppercase(),
                stringResource(R.string.details).uppercase()
            ).forEachIndexed { index, title ->
                Tab(
                    content = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black)
                                .padding(8.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = title,
                                color = if (selectedTabIndex.value == index) Color.White else Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        selectedTabIndex.value = index
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black,
                )
            }
        }
        when (selectedTabIndex.value) {
            0 -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {
                data.relatedMovies.chunked(4).map {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        it.map { mv ->
                            AsyncImage(
                                modifier = Modifier
                                    .width(75.dp)
                                    .clickable {
                                        onMovieClick(mv.id)
                                    },
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(mv.cover)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = data.name,
                                contentScale = ContentScale.FillWidth,
                            )
                        }

                    }
                }
            }

            1 -> Box {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.datasheet),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Text("${stringResource(R.string.year_production)}: ${data.year}")
                    Text("${stringResource(R.string.country)}: ${data.country}")
                    Text("${stringResource(R.string.producer)}: ${data.producer}")
                    Text(
                        "${stringResource(R.string.budget)}: ${
                            data.revenue.toCurrency("$")
                        }"
                    )
                    Text(
                        "${stringResource(R.string.revenue)}: ${
                            data.revenue.toCurrency("$")
                        }"
                    )
                    Text("${stringResource(R.string.runtime)}: ${data.runtime} minutos")
                    Text("${stringResource(R.string.vote_avarage)}: ${data.vote_average}")
                }
            }
        }
    }
    if (showDialog.value) {
        Box(
            modifier = Modifier
                .background(color = Color.Black)
                .alpha(0.25f)
                .fillMaxSize()
        )
        Dialog(
            onDismissRequest = { showDialog.value = false },
            content = {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            showDialog.value = false
                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(originalImage(data.coverPath))
                        .crossfade(true)
                        .build(),
                    contentDescription = data.name,
                    contentScale = ContentScale.FillWidth,
                )
            }
        )
    }
}
