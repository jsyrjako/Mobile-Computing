package com.jsyrjako.reminderapp.ui.singUp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SingUpScreen(
    modifier: Modifier,
    navController: NavController
){
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

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "Create new Account",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(50.dp))

        Icon(
            painter = rememberVectorPainter(Icons.Rounded.AccountBox),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp),
        )


        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = { text -> username.value = text },
            label = { Text(text = "Email")},
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = { passwordString -> password.value = passwordString },
            label = { Text(text = "Create password")},
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )
        {
            Text(text = "Sing Up")
        }

        Spacer(modifier = Modifier.height(150.dp))
    }
}