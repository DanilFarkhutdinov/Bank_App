package screens

import Client
import Screen
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import navcontroller.NavController

@Composable
fun AuthScreen(
    navController: NavController,
    client: Client
) {
    val login = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }
    var server_ans by remember { mutableStateOf("") }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Column{
            Row {
                Text("Логин", modifier = Modifier.padding(top = 15.dp).padding(horizontal = 18.dp), fontSize = 17.sp)
                TextField(value = login.value,
                    onValueChange = {newText -> login.value = newText},
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.height(50.dp)
                )
            }
            Row (modifier = Modifier.padding(vertical = 10.dp)) {
                Text("Пароль", modifier = Modifier.padding(top = 15.dp).padding(horizontal = 10.dp), fontSize = 17.sp)
                TextField(value = password.value,
                    onValueChange = {newText -> password.value = newText},
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.height(50.dp),
                    visualTransformation = PasswordVisualTransformation(),
                )
            }
            Row (modifier = Modifier.padding(vertical = 10.dp)){
                Button(
                    onClick = {
                        client.start()
                        client.push("auth" + " " + login.value + " " + password.value)
                        server_ans = client.pull().toString()
                        client.finish()
                    },
                    modifier = Modifier.padding(start = 131.dp)){
                    Text("Вход", fontSize = 17.sp)
                    if (server_ans == "0_auth"){
                        client.login = login.value
                        navController.navigate(Screen.ProfileScreen.name)
                    }
                }
                Button(
                    onClick = {
                        navController.navigate(Screen.RegScreen.name)
                },
                    modifier = Modifier.padding(horizontal = 10.dp)){
                    Text("Регистрация", fontSize = 17.sp)
                }
            }
            Row{
                when (server_ans){
                    "1_auth" -> Text("Заполните все поля", modifier = Modifier.padding(start = 150.dp), color = Color.Red)
                    "2_auth" -> Text("Пользователя с указанным логином не существует", modifier = Modifier.padding(start = 35.dp), color = Color.Red)
                    "3_auth" -> Text("Неверно введен пароль", modifier = Modifier.padding(start = 135.dp), color = Color.Red)
                }
            }
        }
    }
}