package com.example.foodorderingapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.models.FoodCategory

import com.example.foodorderingapp.presentation.utils.SearchBarDiningTabScreen

@Composable
fun SearchBarScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .systemBarsPadding()) {
        var searchQuery by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrowback),
                        contentDescription = "Search",
                        tint = colorResource(R.color.purple_200),
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Search text field
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                        },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray
                        ),
                        decorationBox = { innerTextField ->
                            Box {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Search \"Restaurant\"",
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                                innerTextField()
                            }
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    VerticalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        thickness = 0.5.dp, color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "Voice Search",
                        tint = colorResource(R.color.purple_700),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        TabItem()
    }
}

@Composable
fun TabItem() {
    val tabItems = listOf(
        com.example.foodorderingapp.data.models.TabItem(title = "Delivery"),
        com.example.foodorderingapp.data.models.TabItem(title = "Dining")
    )

    var selectedIndex by remember {
        mutableIntStateOf( 0)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    color = colorResource(R.color.purple_500)
                )
            }
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedIndex,
                    selectedContentColor = colorResource(R.color.purple_500),
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        selectedIndex = index
                    },

                    text = {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

// Directly show the screen based on selected index
        when (selectedIndex) {
            0 -> FoodCategoryList()
            1 -> SearchBarDiningTabScreen()
        }
    }
}


@Composable
fun FoodCategoryList(modifier: Modifier = Modifier) {
    // Define the food categories
    Text(
        text = "WHAT'S ON YOUR MIND?",
        style = TextStyle(
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            letterSpacing = 2.sp,
            fontFamily = FontFamily.SansSerif
        ),
     modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
        textAlign = TextAlign.Center
    )

    val foodCategories = listOf(
        FoodCategory(name = "ALL", R.drawable.allfood),
        FoodCategory(name = "Burger", R.drawable.burger),
        FoodCategory(name = "Pizza", R.drawable.pizza_image),
        FoodCategory(name = "Sweets", R.drawable.sweets),
        FoodCategory(name = "Biryani", R.drawable.vegbiryani),
        FoodCategory(name = "Deserts", R.drawable.ice_cream),
        FoodCategory(name = "ROLLs", R.drawable.chinese),
        FoodCategory(name = "Pasta", R.drawable.pasta),
        FoodCategory(name = "Chini̇se", R.drawable.chinese),
        FoodCategory(name = "Burger", R.drawable.burger),
        FoodCategory(name = "Pizza", R.drawable.burger),
        FoodCategory(name = "Sweets", R.drawable.foodbag),
        FoodCategory(name = "Biryani", R.drawable.vegbiryani),
        FoodCategory(name = "Deserts", R.drawable.ice_cream),
        FoodCategory(name = "ROLLs", R.drawable.rolls),
        FoodCategory(name = "Pasta", R.drawable.pasta),
        FoodCategory(name = "Chini̇se", R.drawable.chinese),
        FoodCategory(name = "Sweets", R.drawable.sweets),
        FoodCategory(name = "Biryani", R.drawable.vegbiryani),
        FoodCategory(name = "Deserts", R.drawable.ice_cream),
        FoodCategory(name = "ROLLs", R.drawable.rolls),
        FoodCategory(name = "Pasta", R.drawable.pasta),
        FoodCategory(name = "Chini̇se", R.drawable.chinese),
        FoodCategory(name = "Sweets", R.drawable.sweets)
    )

    // State to track selected category
    var selectedCategoryIndex by remember { mutableIntStateOf(value = 0) }

    // Use LazyVerticalGrid for grid layout with vertical scrolling
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3), // Display 3 items per row
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(foodCategories.size) { index ->
            val category = foodCategories[index]
            FoodCategoryItem(
                category = category,
                isSelected = index == selectedCategoryIndex,
                onClick = { selectedCategoryIndex = index }
            )
        }
    }
}
@Composable
fun FoodCategoryItem(
    category: FoodCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
    ) {
        Image(
            painter = painterResource(id = category.imageRes),
            contentDescription = category.name,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold ,
                color = Color.DarkGray,

            textAlign = TextAlign.Center
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
        )

    }
}