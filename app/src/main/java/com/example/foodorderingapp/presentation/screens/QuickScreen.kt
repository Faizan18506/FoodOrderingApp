package com.example.foodorderingapp.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodorderingapp.R
import com.example.foodorderingapp.presentation.components.HomeScreenCard
import com.example.foodorderingapp.presentation.components.TopAppBarDiningScreen
import com.example.foodorderingapp.presentation.utils.BottomSheetToAddProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickScreen(navController: NavController, listState: LazyListState) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var showBottomSheet = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBarDiningScreen(scrollBehavior, navController)
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(vertical = 0.dp),
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(bottom = innerPadding.calculateBottomPadding())
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(R.drawable.quickbanner),
                        modifier = Modifier.fillMaxSize().clip(
                            shape = RoundedCornerShape(
                                bottomStart = 15.dp,
                                bottomEnd = 15.dp
                            )
                        ),
                        contentDescription = "Quick Screen"
                    )
                }
            }
            item {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(count = 6) {
                        QuickScreenComponents(
                            navController,
                            onAddClick = { showBottomSheet.value = true })
                    }
                }
                if (showBottomSheet.value) {
                    BottomSheetToAddProduct(
                        onDismiss = { showBottomSheet.value = false },
                        navController = navController
                    )
                }
            }

            items(5) {
                Spacer(modifier = Modifier.height(16.dp))
                HomeScreenCard(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun QuickScreenComponents(navController: NavController, onAddClick: () -> Unit) {
    var productName by remember { mutableStateOf( "Peri Peri Burger") }
    var rating by remember { mutableStateOf( "4.2") }
    var price by remember { mutableStateOf( "₹249") }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.width(200.dp)) {
                Row(
                    modifier = Modifier.wrapContentSize().padding(start = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.veg_icon),
                        modifier = Modifier.size(17.dp),
                        tint = colorResource(R.color.purple_500),
                        contentDescription = null
                    )
                    Card(
                        modifier = Modifier.padding(start = 8.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.purple_200).copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = "Bestseller",
                            modifier = Modifier.padding(horizontal = 3.dp),
                            color = colorResource(R.color.purple_700),
                            fontSize = 11.sp
                        )
                    }
                }
                    Text(
                        text = productName,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.purple_500),
                        modifier = Modifier.padding(horizontal = 1.dp, vertical = 8.dp),
                        fontSize = 18.sp
                    )

                    Card(
                        modifier = Modifier.size(width = 50.dp, height = 22.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = CardDefaults.cardColors(colorResource(R.color.purple_500))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = rating,
                                modifier = Modifier.padding(start = 3.dp, top = 2.dp),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Icon(
                                painterResource(R.drawable.star),
                                modifier = Modifier.padding(start = 3.dp, top = 2.dp, end = 3.dp).size(12.dp),
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                    }

                    Text(
                        text = price,
                        modifier = Modifier.padding(horizontal = 1.dp, vertical = 8.dp),
                        color = colorResource(R.color.purple_200),
                        fontSize = 16.sp
                    )

                    Box(
                        modifier = Modifier.size(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.burger3),
                            modifier = Modifier.size(130.dp).clip(shape = RoundedCornerShape(15.dp)),
                            contentDescription = "Product Image",
                            alignment = Alignment.Center
                        )

                    Card(
                        onClick = { onAddClick() },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 120.dp)
                            .size(width = 100.dp, height = 36.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(colorResource(R.color.purple_200)),
                        border = BorderStroke(width = 1.dp, color = colorResource(R.color.purple_700))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "ADD",
                                modifier = Modifier.padding(start = 30.dp, top = 6.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(R.color.purple_500),
                                fontSize = 20.sp
                            )
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_24),
                                contentDescription = "Add item",
                                tint = Color.Red,
                                modifier = Modifier.padding(3.dp).size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}