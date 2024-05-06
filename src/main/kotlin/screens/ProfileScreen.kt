package screens

import Client
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import navcontroller.NavController
import java.text.NumberFormat
import java.util.*

@Composable
fun ProfileScreen(
    navController: NavController,
    client: Client
) {
    var server_ans by remember { mutableStateOf("") }
    if (client.login == null){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.AuthScreen.name)
                },
                modifier = Modifier.padding(horizontal = 10.dp)){
                Text("Войти в аккаунт", fontSize = 17.sp)
            }
        }
    }
    else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            client.start()
            client.push("profile" + " " + client.login)
            server_ans = client.pull().toString()
            client.finish()
            var info_user = server_ans.split("\\s".toRegex()).toList()

            Text("Приветствуем, ${info_user[0]}", fontSize = 30.sp, modifier = Modifier.padding(top = 150.dp))
            Text("Номер счета ${info_user[1]}",  fontSize = 30.sp, modifier = Modifier.padding(top = 50.dp))
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
            val symbol = numberFormat.currency?.symbol
            Text("${info_user[2]} $symbol",  fontSize = 30.sp, modifier = Modifier.padding(top = 50.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.AuthScreen.name)
                    client.login = null
                },
                modifier = Modifier.padding(start = 450.dp, top = 150.dp)
            ){
                Text("Выйти из аккаунта", fontSize = 17.sp)
            }
        }

    }
}
