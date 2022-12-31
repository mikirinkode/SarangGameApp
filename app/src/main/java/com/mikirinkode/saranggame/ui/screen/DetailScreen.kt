package com.mikirinkode.saranggame.ui.screen

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mikirinkode.saranggame.R
import com.mikirinkode.saranggame.data.remote.response.GenresItem
import com.mikirinkode.saranggame.ui.components.MessageCard
import com.mikirinkode.saranggame.ui.theme.Dark500
import com.mikirinkode.saranggame.ui.theme.SarangGameTheme
import com.mikirinkode.saranggame.utils.UiState
import com.mikirinkode.saranggame.viewmodel.GameViewModel
import com.mikirinkode.saranggame.viewmodel.ViewModelFactory
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.text.DecimalFormat

@Composable
fun DetailScreen(
    gameId: Int,
    onShareClick : (String) -> Unit,
    openWebsite : (String) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val viewModel: GameViewModel =
        viewModel(factory = ViewModelFactory.getInstance(context))

    var isFavorite by remember {
        mutableStateOf(false)
    }

    viewModel.gameDetailState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getGameDetail(gameId.toString())
            }
            is UiState.Success -> {
                uiState.data.let {
                    val genres = it.genres ?: listOf()
                    val rating = DecimalFormat("#.#").format(it.rating?.times(2))
                    val description = Html.fromHtml(it.description ?: "").toString()
                    DetailContent(
                        title = it.name ?: "",
                        description = description,
                        releaseDate = it.released ?: "",
                        rating = rating,
                        genres = genres,
                        website = it.website ?: "",
                        imageUrl = it.backgroundImage ?: "",
                        isFavorite = isFavorite,
                        onFavoriteClick = {
                            isFavorite = !isFavorite
                            if (isFavorite) {
                            } else {
                            }
                        },
                        onShareClick = onShareClick,
                        openWebsite = openWebsite,
                        navigateBack = navigateBack
                    )
                }
            }
            is UiState.Error -> {
                MessageCard(
                    icon = Icons.Rounded.Warning,
                    title = "Terjadi Masalah.",
                    description = "Maaf terjadi masalah, silahkan periksa koneksi internet anda."
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    title: String,
    description: String,
    releaseDate: String,
    rating: String,
    genres: List<GenresItem>,
    website: String,
    imageUrl: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onShareClick: (String) -> Unit,
    openWebsite: (String) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {


    val state = rememberCollapsingToolbarScaffoldState()



    Box {
        CollapsingToolbarScaffold(
            modifier = modifier.fillMaxSize(),
            state = state,
            toolbarModifier = Modifier.background(Color.Black),
            enabled = true,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )

                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.background)
                        .parallax(0.5f)
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(CutCornerShape(bottomStart = 64.dp))
                        .graphicsLayer {
                            // change alpha of image as the toolbar expands
                            alpha = state.toolbarState.progress
                        }
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Game Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.background)
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(CutCornerShape(bottomStart = 64.dp))
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        for (genre in genres) {
                            GenreCard(genre = genre.name ?: "")
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .pin()
                        .padding(8.dp)
                        .background(
                            color = Color.Black,
                            shape = MaterialTheme.shapes.small
                        )
                        .align(Alignment.CenterStart)
                ) {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back Button",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = MaterialTheme.shapes.small.copy(
                                        bottomStart = CornerSize(
                                            0.dp
                                        )
                                    )
                                )
                        ) {
                            Card(
                                shape = MaterialTheme.shapes.small,
                                backgroundColor = Dark500,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = title,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Card(
                            shape = MaterialTheme.shapes.small,
                            backgroundColor = Dark500,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Text(
                                        text = "Rating",
                                        color = MaterialTheme.colors.primary,
                                        fontSize = 12.sp,
                                    )
                                    Text(
                                        text = rating,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Text(
                                        text = "Release Date",
                                        color = MaterialTheme.colors.primary,
                                        fontSize = 12.sp,
                                    )
                                    Text(
                                        text = releaseDate,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(16.dp))
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Card(
                                shape = MaterialTheme.shapes.small,
                                backgroundColor = Dark500,
                                modifier = Modifier.weight(1f),
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Description",
                                        color = MaterialTheme.colors.primary,
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))

                                    Text(
                                        text = description,
                                        color = Color.White,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                    )

                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                SmallIconButton(
                                    icon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outlined,
                                    contentDescription = "Favorite Button",
                                    onClick = onFavoriteClick
                                )
                                SmallIconButton(
                                    icon = R.drawable.ic_share,
                                    contentDescription = "Share Button",
                                    onClick = { onShareClick(title) }
                                )
                                SmallIconButton(
                                    icon = R.drawable.ic_web,
                                    contentDescription = "Open Website Button",
                                    onClick = { openWebsite(website) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SmallIconButton(
    icon: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = Dark500,
                shape = CutCornerShape(
                    topStart = 8.dp,
                    topEnd = 12.dp,
                    bottomEnd = 8.dp,
                    bottomStart = 12.dp
                )
            )
//            .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.small)
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun GenreCard(
    genre: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color.Black.copy(0.7f),
                shape = CutCornerShape(
                    topStart = 4.dp,
                    topEnd = 8.dp,
                    bottomEnd = 4.dp,
                    bottomStart = 8.dp
                )
            )
//            .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.small)
    ) {
        Text(
            text = genre,
            color = MaterialTheme.colors.primary,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    SarangGameTheme {
        DetailScreen(0, {},{}, {})
    }
}