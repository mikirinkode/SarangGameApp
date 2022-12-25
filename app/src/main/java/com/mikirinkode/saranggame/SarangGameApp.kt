package com.mikirinkode.saranggame

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikirinkode.saranggame.utils.UiState
import com.mikirinkode.saranggame.viewmodel.GameViewModel
import com.mikirinkode.saranggame.viewmodel.ViewModelFactory


@Composable
fun SarangGameApp(

) {
    val context = LocalContext.current
    val viewModel: GameViewModel =
        viewModel(factory = ViewModelFactory.getInstance(context))

    viewModel.listState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                Log.e("Sarang Game APP", "GET LIST LOADING")
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = "LOADING", modifier = Modifier.align(Alignment.Center))
                }
                viewModel.getGameList()
            }
            is UiState.Success -> {
                Log.e("Sarang Game APP", "GET LIST SUCCESS")

                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = "SUCCESS", modifier = Modifier.align(Alignment.Center))
                }
            }
            is UiState.Error -> {
                Log.e("Sarang Game APP", "GET LIST ERROR")

                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = "ERROR", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Preview
@Composable
fun SarangGameAppPreview() {

}