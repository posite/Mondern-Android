package com.posite.modern.ui.meal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.posite.modern.data.model.Category

class MealActivity : ComponentActivity() {
    private val viewModel: MealViewModel by viewModels<MealViewModelImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealCategories(viewModel)
        }
    }
}


@Composable
fun MealCategories(viewModel: MealViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        LaunchedEffect(Unit) {
            viewModel.getCategories()
        }
        val categories by viewModel.categories.collectAsState()
        //이미지들이 한번에 로드되는 것 처럼 보이려면 isLoading 으로 확인 필요
        when (viewModel.isLoding.value) {
            true -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            false -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(categories) {
                        MealCategory(it)
                    }
                }
            }
        }


    }
}

@Composable
fun MealCategory(category: Category) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.strCategoryThumb)
                .crossfade(true)
                .build(),
            contentDescription = category.strCategoryDescription
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.strCategory,
            style = TextStyle(fontSize = 20.sp)
        )

    }
}