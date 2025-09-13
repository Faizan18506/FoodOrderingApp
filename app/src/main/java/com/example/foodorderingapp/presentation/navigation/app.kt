package com.example.foodorderingapp.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bottombar.AnimatedBottomBar
import com.example.foodorderingapp.R
import com.example.foodorderingapp.presentation.screens.DeliveryScreen
import com.example.foodorderingapp.presentation.screens.DiningScreen
import com.example.foodorderingapp.presentation.screens.FinalCheckoutScreen
import com.example.foodorderingapp.presentation.screens.LoginScreen1
import com.example.foodorderingapp.presentation.screens.ParticularCardScreen
import com.example.foodorderingapp.presentation.screens.QuickScreen
import com.example.foodorderingapp.presentation.screens.SearchBarScreen
import com.example.foodorderingapp.presentation.screens.SignUpScreen

data class BottomNavItem(
    val title: String,
    val icon: Painter
)

@Composable
fun App(
    isVisible: Boolean,
    listState: LazyListState
) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    var shouldShowBotoomBar by remember { mutableStateOf(false) }
    LaunchedEffect(currentDestination) {
        shouldShowBotoomBar = when (currentDestination) {
            Routes.DeliveryScreen::class.qualifiedName,
            Routes.QuickScreen::class.qualifiedName,
            Routes.DiningScreen::class.qualifiedName -> true

            else -> false
        }
    }
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val bottomNavItems = listOf(
        BottomNavItem(title = "Delivery", icon = painterResource(id = R.drawable.delivery_cart)),
        BottomNavItem(title = "Quick", icon = painterResource(id = R.drawable.quick_icon)),
        BottomNavItem(title = "Dining", icon = painterResource(id = R.drawable.dining)),

        )
    val selectedColour = colorResource(id = R.color.purple_500)
    val bottomBarHeight by animateDpAsState(
        targetValue = if (isVisible) 64.dp else 0.dp
    )
    var startScreen = if (true) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen

    }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                modifier = Modifier
                    .padding(
                        WindowInsets.navigationBars.only(WindowInsetsSides.Bottom).asPaddingValues()
                    )
                    .fillMaxWidth()
                    .height(bottomBarHeight),
                visible = shouldShowBotoomBar,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(bottomBarHeight)
                        .background(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(Color.LightGray.copy(alpha = 0.2f)), // Background for the whole row
                    ) {
                        bottomNavItems.forEachIndexed { index, _ ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .weight(1f) // Distributes equal width among tabs
                                    .height(3.dp)
                                    .clip(shape = RoundedCornerShape(100.dp))
                                    .background(if (index == selectedItemIndex) selectedColour else Color.Transparent)
                            )
                        }
                    }
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        shadowElevation = 8.dp,
                    ) {
                        AnimatedBottomBar(
                            containerColor = Color.White,
                            animationSpec = spring(
                                dampingRatio = 1f,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        ) {
                            bottomNavItems.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    modifier = Modifier.align(alignment = Alignment.Top),
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        // When condition should define here to navigate
                                        when (selectedItemIndex) {
                                            0 -> navController.navigate(Routes.DeliveryScreen)
                                            1 -> navController.navigate(Routes.QuickScreen)
                                            2 -> navController.navigate(Routes.DiningScreen)
                                        }
                                    },
                                    label = {
                                        if (index == selectedItemIndex) {
                                            Text(
                                                text = item.title,
                                                color = selectedColour,
                                                fontSize = 16.sp
                                            )
                                        } else {
                                            Text(
                                                text = item.title,
                                                color = Color.Gray,
                                                fontSize = 16.sp
                                            )
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = item.icon,
                                            contentDescription = item.title,
                                            modifier = Modifier.size(24.dp),
                                            tint = if (index == selectedItemIndex) {
                                                selectedColour
                                            } else Color.Gray
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }) {
        it
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = startScreen) {
                navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreen1(
                            navController = navController,
                        )
                    }
                    composable<Routes.SignUpScreen> {
                        SignUpScreen(
                            navController = navController
                        )
                    }
                }
                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.DeliveryScreen) {
                    composable<Routes.DeliveryScreen> {
                        DeliveryScreen(navController, listState)
                    }
                    composable<Routes.QuickScreen> {
                        QuickScreen(navController,listState)
                    }
                    composable<Routes.DiningScreen> {
                        DiningScreen(navController,listState)
                    }
//                    composable<Routes.ProfileScreen> {
//                        ProfileScreen(navController)
//                    }
                    composable<Routes.ParticularCardScreen> {
                        ParticularCardScreen(navController)
                    }

                    composable<Routes.FinalCheckoutScreen> {
                        FinalCheckoutScreen(navController)
                    }

                    composable<Routes.SearchBarScreen> {
                        SearchBarScreen(modifier = Modifier, navController)
                    }
                }
            }
        }


    }
}