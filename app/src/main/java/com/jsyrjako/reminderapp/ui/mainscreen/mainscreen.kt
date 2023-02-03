package com.jsyrjako.reminderapp.ui.mainscreen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    modifier: Modifier,
    navController: NavController
){

    Column (
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = rememberVectorPainter(Icons.Rounded.AccountCircle),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().size(150.dp),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate("Login") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(50.dp))
        )
        {
            Text(text = "Sign In")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate("SignUp") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(50.dp)),
        )
        {
            Text(text = "Sign Up")
        }
    }
}