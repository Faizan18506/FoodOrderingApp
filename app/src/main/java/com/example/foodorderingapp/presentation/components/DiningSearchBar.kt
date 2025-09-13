package com.example.foodorderingapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodorderingapp.R

@Composable
fun DiningSearchBar(navController: NavController) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .shadow(2.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp) // ✅ Added padding to keep items inside neatly
            .clickable { /* Handle click event */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔍 Search Icon
        Icon(
            Icons.Default.Search,
            tint = colorResource(R.color.purple_500),
            contentDescription = "Search Icon",
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 📝 TextField
        BasicTextField(
            value = query,
            onValueChange = { query = it },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .weight(1f) // ✅ Takes remaining space between icons
                .padding(horizontal = 8.dp),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    androidx.compose.material.Text(
                        text = "Restaurant name, or a dish...",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )

        // 🎤 Voice Search Icon
        Icon(
            painter = painterResource(R.drawable.mic),
            contentDescription = "Voice Search",
            tint = colorResource(R.color.purple_500),
            modifier = Modifier.size(24.dp)
        )
    }
}
