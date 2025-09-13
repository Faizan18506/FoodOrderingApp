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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.models.UserData
import com.example.foodorderingapp.presentation.components.loginscreencomponent.CheckboxComponent
import com.example.foodorderingapp.presentation.components.loginscreencomponent.OrComponent
import com.example.foodorderingapp.presentation.navigation.Routes
import com.example.foodorderingapp.presentation.navigation.SubNavigation
import com.example.foodorderingapp.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.signUpScreenState.collectAsStateWithLifecycle()
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
        var fullName by remember { mutableStateOf(value = "") }
        var email by remember { mutableStateOf(value = "") }
        var address by remember { mutableStateOf(value = "") }
        var password by remember { mutableStateOf(value = "") }
        var confirmPassword by remember { mutableStateOf(value = "") }
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(bottom = innerPadding.calculateBottomPadding())
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(R.drawable.komatosignup),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Zomato Image"
                        )
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .padding(vertical = 50.dp, horizontal = 16.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                                .size(width = 78.dp, height = 35.dp),
                            colors = ButtonDefaults.buttonColors(
                                 Color.DarkGray.copy(alpha = 0.8f),
                                 Color.LightGray
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
                            text = "Get Started",
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "by creating a free account",
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    MyTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholderValue = "Full Name",
                        painterResource(R.drawable.userprofile)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MyTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholderValue = "Email",
                        painterResource(R.drawable.mail)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MyTextField(
                        value = address,
                        onValueChange = { address = it },
                        placeholderValue = "Address",
                        painterResource(R.drawable.address)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PasswordTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholderValue = "Password",
                        painterResource(R.drawable.padlock)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    PasswordTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholderValue = "Confirm Password",
                        painterResource(R.drawable.padlock)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CheckboxComponent(value = stringResource(id = R.string.term_conditions))
                    Spacer(modifier = Modifier.height(20.dp))

                    val context = LocalContext.current

                    Button(
                        onClick = {
                            if (fullName.isNotBlank() && email.isNotBlank() && address.isNotBlank()
                                && password.isNotBlank() && confirmPassword.isNotBlank()
                            ) {
                                if (password == confirmPassword) {
                                    val userData = UserData(
                                        userName = fullName,
                                        email = email,
                                        address = address,
                                        password = password
                                    )
                                    viewModel.registerUser(userData)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Password and Confirm Password do not match",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please Fill All Fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 14.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.addButtonRed)),
                        elevation = ButtonDefaults.elevation(4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            Arrangement.Center
                        ) {
                            Text(
                                text = "Next",
                                fontSize = 22.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_forward_ios_24),
                                contentDescription = null,
                                modifier = Modifier.padding(top = 4.dp).size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        Arrangement.Center
                    ) {
                        Text(text = "Already a member?")
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Login in",
                            modifier = Modifier.clickable {
                                navController.navigate(Routes.LoginScreen)
                            },
                            color = colorResource(R.color.addButtonRed)
                        )
                    }

                    OrComponent()
                }
            }
        }
    }
}

@Composable
fun MyTextField(
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
            .padding(start = 16.dp, end = 16.dp),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions.Default,
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
fun PasswordTextField(
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
            .padding(start = 16.dp, end = 16.dp),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                    tint = Color.Gray
                )
            }
        },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()


    )
}