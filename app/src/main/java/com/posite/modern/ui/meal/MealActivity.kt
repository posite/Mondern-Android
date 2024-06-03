package com.posite.modern.ui.meal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.posite.modern.data.model.meal.Category

class MealActivity : ComponentActivity() {
    private val viewModel: MealViewModel by viewModels<MealViewModelImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MealScreen(viewModel, navController)
        }
    }
}

@Composable
fun MealScreen(viewModel: MealViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = MealScreen.MealCategories.route) {
        composable(MealScreen.MealCategories.route) {
            MealCategories(viewModel) {
                navController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                Log.d("Category", "category: $it")
                navController.navigate(MealScreen.MealDetail.route)
            }
        }
        composable(MealScreen.MealDetail.route) {
            val category =
                navController.previousBackStackEntry?.savedStateHandle?.get<Category>("category")
                    ?: Category("", "", "")
            Log.d("Detail", "category: $category")
            MealDetail(category = category)
        }
    }
}


@Composable
fun MealCategories(viewModel: MealViewModel, navigateToDetail: (Category) -> Unit) {
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
                        MealCategory(it, navigateToDetail)
                    }
                }
            }
        }


    }
}

@Composable
fun MealCategory(category: Category, navigateToDetail: (Category) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                navigateToDetail(category)
            },
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

@Composable
fun MealDetail(category: Category) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.strCategoryThumb)
                .crossfade(true)
                .build(),
            contentDescription = category.strCategoryDescription,
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f)
        )
        Text(
            text = category.strCategoryDescription,
            textAlign = TextAlign.Justify,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}