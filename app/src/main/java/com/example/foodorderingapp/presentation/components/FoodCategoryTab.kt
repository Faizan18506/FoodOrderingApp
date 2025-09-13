package com.example.foodorderingapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.models.FoodCategoryData

@Composable
fun FoodCategoryTab(
    modifier: Modifier,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
) {
    val categories = listOf(
        FoodCategoryData(name = "All", R.drawable.allfood),
        FoodCategoryData(name = "Pizza", R.drawable.pizza_image),
        FoodCategoryData(name = "Chinese", R.drawable.chinese),
        FoodCategoryData(name = "Burgers", R.drawable.burger),
        FoodCategoryData(name = "Biryani", R.drawable.vegbiryani),
        FoodCategoryData(name = "Sweets", R.drawable.sweets),
        FoodCategoryData(name = "Pasta", R.drawable.pasta),
        FoodCategoryData(name = "Rolls", R.drawable.rolls),
        FoodCategoryData(name = "Ice Cream", R.drawable.ice_cream)
    )
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.White,
        contentColor = Color.Black,
        edgePadding = 8.dp,
        indicator = {tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(3.dp), // Customize thickness if needed
                color = Color.Red
            )
        },
        divider = {
            HorizontalDivider(color = Color.LightGray, thickness = 0.6.dp) // <-- customize divider
        }
    )
    {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(8.dp)
                ) {
                    AsyncImage(
                        model = category.image,
                        contentDescription = category.name,
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        text = category.name,
                        fontSize = 12.sp,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTabIndex == index) Color.Black else Color.DarkGray
                    )
                }
            }
        }
    }
}