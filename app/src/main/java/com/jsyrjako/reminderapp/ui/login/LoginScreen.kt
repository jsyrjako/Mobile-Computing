package com.jsyrjako.reminderapp.ui.login

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    modifier: Modifier
){
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column (
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
            ) {

//        Image(
 //            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = "login_image",
//            modifier = Modifier.fillMaxWidth(),
//            alignment = Alignment.Center
//        )
        
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
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )
        {
            Text(text = "Login")
        }
    }
}