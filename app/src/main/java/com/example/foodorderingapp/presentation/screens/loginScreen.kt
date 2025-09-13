package com.example.foodorderingapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.models.UserData
import com.example.foodorderingapp.presentation.components.loginscreencomponent.OrComponent
import com.example.foodorderingapp.presentation.navigation.Routes
import com.example.foodorderingapp.presentation.navigation.SubNavigation
import com.example.foodorderingapp.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen1(
    viewmodel: AuthViewModel = hiltViewModel(), navController: NavController
) {
    val state = viewmodel.loginScreenState.collectAsStateWithLifecycle()
    val showDilog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    } else if (state.value.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = state.value.errorMessage!!)
        }
    } else if (state.value.userData != null) {
        navController.navigate(SubNavigation.MainHomeScreen)
    } else {
        var email by remember { mutableStateOf(value = "") }
        var password by remember { mutableStateOf(value = "") }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.foodo),
                    contentDescription = "Komato Image"
                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(vertical = 50.dp, horizontal = 16.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .size(width = 78.dp, height = 35.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.8f),
                        contentColor = Color.LightGray
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Skip",
                        modifier = Modifier.clickable {
                            navController.navigate(SubNavigation.MainHomeScreen)
                        }
                    )

                }
            }
            Spacer(modifier = Modifier.height(6.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pakistan's #1 Food Delivery",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "and Dining App",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.width(130.dp),
                    color = Color.LightGray,
                    thickness = 0.8.dp
                )

                Text(
                    text = "Login or sign up",
                    modifier = Modifier.padding(8.dp),
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                HorizontalDivider(
                    modifier = Modifier.width(130.dp),
                    color = Color.LightGray,
                    thickness = 0.8.dp
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            EmailTextField(
                value = email,
                onValueChange = { email = it },
                placeholderValue = "Email",
                painterResource(R.drawable.mail)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LoginPasswordTextField(
                value = password,
                onValueChange = { password = it },
                placeholderValue = "Password",
                painterResource(R.drawable.padlock)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        val userData = UserData(
                            email = email,
                            password = password
                        )
                        viewmodel.loginUser(userData)
                    } else {
                        Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.addButtonRed)),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    Arrangement.Center
                ) {
                    Text(
                        text = "Login",
                        fontSize = 22.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.Center
            ) {
                Text(
                    text = "Don't have a account?",
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "Signup",
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.SignUpScreen)
                    },
                    color = colorResource(R.color.addButtonRed),
                )
            }

            OrComponent()
            Spacer(modifier = Modifier.height(1.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By Continuing, you agree",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Terms of Services Privacy Policy Content Policy",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderValue: String,
    painterResource: Painter
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholderValue, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(13.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "Icon",
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
        }
    )
}

@Composable
fun LoginPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderValue: String,
    painterResource: Painter
) {
    val passwordVisible = remember { mutableStateOf(value = false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholderValue, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "Icon",
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    imageVector = iconImage,
                    contentDescription = description,
                    tint = Color.Gray)
            }
        },

        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

    )
}