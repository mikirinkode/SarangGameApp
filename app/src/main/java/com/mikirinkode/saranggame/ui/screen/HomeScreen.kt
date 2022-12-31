package com.mikirinkode.saranggame.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikirinkode.saranggame.R
import com.mikirinkode.saranggame.data.remote.response.GenresItem
import com.mikirinkode.saranggame.ui.components.GameItemCard
import com.mikirinkode.saranggame.ui.components.LoadingIndicator
import com.mikirinkode.saranggame.ui.components.MessageCard
import com.mikirinkode.saranggame.ui.theme.Dark500
import com.mikirinkode.saranggame.ui.theme.Dark700
import com.mikirinkode.saranggame.ui.theme.SarangGameTheme
import com.mikirinkode.saranggame.utils.UiState
import com.mikirinkode.saranggame.viewmodel.GameViewModel
import com.mikirinkode.saranggame.viewmodel.ViewModelFactory
import java.text.DecimalFormat

@Composable
fun HomeScreen(
    navigateToAbout: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: GameViewModel =
        viewModel(factory = ViewModelFactory.getInstance(context))

    val query by viewModel.query

    Column(
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "SarangGame",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = navigateToAbout) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(R.string.about_page),
                        tint = MaterialTheme.colors.primary
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.background
        )
        SearchBar(
            inputValue = query,
            onValueChange = viewModel::search,
            onClickTrailingIcon = viewModel::clearQuery)
        Box(modifier = Modifier) {
            viewModel.listState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        if (query.isNotEmpty()){
                            viewModel.search(query)
                        } else {
                            viewModel.getGameList()
                        }
                        LoadingIndicator()
                    }
                    is UiState.Success -> {
                        if (uiState.data.isNotEmpty()) {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 16.dp)
                            ) {
                                items(uiState.data) { game ->
                                    val genres = getGenres(game.genres)
                                    val rating = DecimalFormat("#.#").format(game.rating?.times(2))
                                    GameItemCard(
                                        imageUrl = game.backgroundImage.toString(),
                                        title = game.name.toString(),
                                        rating = rating.toString(),
                                        genres = genres,
                                        onItemClick = { game.id?.let { navigateToDetail(it) } }
                                    )
                                }
                            }
                        } else {
                            MessageCard(
                                icon = Icons.Default.Info,
                                title = "No Data.",
                                description = "Maaf terjadi masalah, tidak ada data yang tersedia saat ini."
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
    }
}


@Composable
fun SearchBar(
    inputValue: String,
    onValueChange: (String) -> Unit,
    onClickTrailingIcon: () -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        value = inputValue,
        textStyle = TextStyle(
            color = Color.White
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (inputValue.isNotEmpty()) {
                IconButton(onClick = onClickTrailingIcon) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colors.onSurface,
                        contentDescription = "Close Button",
                    )
                }
            }
        },
        placeholder = {
            Text(text = "Search")
        },
        onValueChange = onValueChange,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .background(color = Dark500, shape = MaterialTheme.shapes.small)
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.small)
        ,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions()
    )
}

fun getGenres(genres: List<GenresItem?>?): String {
    val result: String
    val genreList = ArrayList<String>()
    genres?.forEach { genreList.add(it?.name ?: "") }
    result = genreList.joinToString(separator = ", ")
    return result
}

@Preview
@Composable
fun HomeScreenPreview() {
    SarangGameTheme {
        HomeScreen({}, {})
    }
}