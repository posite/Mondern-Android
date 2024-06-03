package com.posite.modern.ui.meal

sealed class MealScreen(val route: String) {
    object MealCategories : MealScreen("meal_categories")
    object MealDetail : MealScreen("meal_detail")

}