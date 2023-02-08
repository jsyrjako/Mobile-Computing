package com.jsyrjako.reminderapp.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jsyrjako.reminderapp.data.sharedPreferences.SharedPreferences

@Composable
fun Profile(
    //onBackPress: () -> Unit,
    navController: NavController,
) {
    Surface {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        ) {
            TopAppBar (
                modifier= Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }

                Text(text = "Profile")

                Spacer(modifier = Modifier.fillMaxWidth(0.7f))
                
                TextButton({ navController.navigate("mainScreen") }) {
                    Text("LogOut")

                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)

            ) {

                Icon(
                    painter = rememberVectorPainter(Icons.Rounded.AccountCircle),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp),
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = SharedPreferences().name,
                    onValueChange = { },
                    label = { Text(text = "Name")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = SharedPreferences().username,
                    onValueChange = { },
                    label = { Text(text = "Username")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Bio")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                {
                    Text(text = "Save")
                }

            }
        }
    }
}