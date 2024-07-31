package com.posite.modern.ui.chat

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.posite.modern.ui.chat.AnimationTransitions.enterChat
import com.posite.modern.ui.chat.AnimationTransitions.enterRooms
import com.posite.modern.ui.chat.AnimationTransitions.enterTransition
import com.posite.modern.ui.chat.AnimationTransitions.exitChat
import com.posite.modern.ui.chat.AnimationTransitions.exitRooms
import com.posite.modern.ui.chat.AnimationTransitions.exitTransition
import com.posite.modern.ui.chat.auth.ChatAuthContractViewModel
import com.posite.modern.ui.chat.auth.LoginScreen
import com.posite.modern.ui.chat.auth.SignUpScreen
import com.posite.modern.ui.chat.chat.ChatContractViewModel
import com.posite.modern.ui.chat.chat.ChatScreen
import com.posite.modern.ui.chat.room.ChatRoomContractViewModel
import com.posite.modern.ui.chat.room.ChatRoomsScreen

@Composable
fun ChatNavigation(
    chatAuthContractViewModel: ChatAuthContractViewModel,
    chatRoomContractViewModel: ChatRoomContractViewModel,
    chatContractViewModel: ChatContractViewModel,
    navController: NavHostController,
    setBackPressedCallback: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ChatScreens.LoginScreen.route
    ) {
        composable(
            ChatScreens.SignupScreen.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition
        ) {
            SignUpScreen(
                viewModel = chatAuthContractViewModel,
                onNavigationToLogin = { navController.navigate(ChatScreens.LoginScreen.route) },
                onSignUpSuccess = {
                    navController.navigate(ChatScreens.ChatRoomsScreen.route) {
                        popUpTo(ChatScreens.SignupScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            ChatScreens.LoginScreen.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition
        ) {
            LoginScreen(
                viewModel = chatAuthContractViewModel,
                onNavigationToSignUp = { navController.navigate(ChatScreens.SignupScreen.route) },
                onLoginSuccess = {
                    navController.navigate(ChatScreens.ChatRoomsScreen.route) {
                        popUpTo(ChatScreens.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            ChatScreens.ChatRoomsScreen.route,
            enterTransition = enterRooms,
            exitTransition = exitRooms
        ) {
            setBackPressedCallback()
            ChatRoomsScreen(viewModel = chatRoomContractViewModel) {
                navController.navigate("${ChatScreens.ChatScreen.route}/${it.id}")
            }
        }
        composable(
            "${ChatScreens.ChatScreen.route}/{roomId}",
            enterTransition = enterChat,
            exitTransition = exitChat
        ) { backStackEntry ->
            val roomId =
                backStackEntry.arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId, chatContractViewModel) {
                navController.navigateUp()
                //chatContractViewModel.setInvisible()
                chatContractViewModel.clearAll()
            }
        }
    }
    /*SideEffect {
        CoroutineScope(Dispatchers.IO).launch {
            navController.visibleEntries.collect {
                Log.d("backstacks", "$it")
            }
        }

    }*/
}

object AnimationTransitions {
    private const val TIME_DURATION = 300

    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(durationMillis = TIME_DURATION, easing = FastOutLinearInEasing)
        )
    }

    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        fadeOut(
            targetAlpha = 0.3f,
            animationSpec = tween(durationMillis = TIME_DURATION, easing = FastOutLinearInEasing)
        )
    }
    val enterChat: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(
                durationMillis = TIME_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }

    val enterRooms: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally(
            initialOffsetX = { -it / 3 },
            animationSpec = tween(
                durationMillis = TIME_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }

    val exitRooms: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutHorizontally(
            targetOffsetX = { -it / 3 },
            animationSpec = tween(
                durationMillis = TIME_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }

    val exitChat: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(
                    durationMillis = TIME_DURATION,
                    easing = FastOutLinearInEasing
                )
            )
        }


}

/*
@ExperimentalAnimationApi
fun NavGraphBuilder.slideComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content:
    @Composable()
    (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route,
        arguments = arguments,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}*/
