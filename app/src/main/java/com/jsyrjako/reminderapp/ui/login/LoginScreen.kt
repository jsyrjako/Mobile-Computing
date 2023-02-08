package com.jsyrjako.reminderapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jsyrjako.reminderapp.data.sharedPreferences.SharedPreferences


@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController
){
    // get username
    val setusername = SharedPreferences().username
    // get password
    val setpassword = SharedPreferences().password

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column (
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
            ) {

        Button(
            onClick = { navController.navigate("mainScreen") },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(contentColor = Color.Blue)
        ){
            Icon(
                painter = rememberVectorPainter(Icons.Rounded.ArrowBack),
                contentDescription = "",
            )
        }

        Spacer(modifier = Modifier.height(150.dp))

        Icon(
            painter = rememberVectorPainter(Icons.Rounded.AccountCircle),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().size(150.dp),
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = { text -> username.value = text },
            label = { Text(text = "Username")},
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = { passwordString -> password.value = passwordString },
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { 
                if (username.value == setusername && password.value == setpassword){
                    navController.navigate("home")
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )
        {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(130.dp))
    }
}