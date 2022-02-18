package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.neatflix.R
import com.ericg.neatflix.composables.IconNext
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor

@Composable
fun SignUpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back icon",
                tint = Color.White.copy(alpha = 0.78F)
            )
            Text(
                text = "Create Account",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.78F),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            modifier = Modifier.padding(vertical = 12.dp),
            contentDescription = "logo"
        )
        var emailInput by remember {
            mutableStateOf("")
        }
        var passwordInput by remember {
            mutableStateOf("")
        }
        var confirmPasswordInput by remember {
            mutableStateOf("")
        }
        var isPasswordVisible by remember {
            mutableStateOf(false)
        }
        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = AppOnPrimaryColor,
            cursorColor = AppOnPrimaryColor,
            leadingIconColor = AppOnPrimaryColor,
            trailingIconColor = AppOnPrimaryColor,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = AppOnPrimaryColor.copy(alpha = 0.5F),
            focusedLabelColor = Color.White,
            unfocusedLabelColor = AppOnPrimaryColor
        )

        OutlinedTextField(
            value = emailInput,
            onValueChange = { newValue ->
                emailInput = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            label = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "icon")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = passwordInput,
            onValueChange = { newValue ->
                passwordInput = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            label = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "icon")
            },
            trailingIcon = {
                val image = if (isPasswordVisible)
                    R.drawable.ic_visibility else R.drawable.ic_visibility_off
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(painter = painterResource(id = image), "toggle icon")
                }
            },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = confirmPasswordInput,
            onValueChange = { newValue ->
                confirmPasswordInput = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            label = {
                Text(text = "Confirm password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "icon")
            },
            trailingIcon = {
                val image = if (isPasswordVisible)
                    R.drawable.ic_visibility else R.drawable.ic_visibility_off
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(painter = painterResource(id = image), "toggle icon")
                }
            },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = textFieldColors
        )
        IconNext {

        }
    }
}

@Preview(device = Devices.PIXEL)
@Composable
fun SignUpScreenPrev() {
    SignUpScreen()
}