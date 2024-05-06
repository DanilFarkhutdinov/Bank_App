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
fun RegScreen(
    navController: NavController,
    client: Client
) {
    val login = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }
    val name = remember{ mutableStateOf("") }
    val re_password = remember{ mutableStateOf("") }
    val acc_num = remember{ mutableStateOf("") }
    var server_ans by remember{ mutableStateOf("") }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Column{
            Row {
                Text("Имя", modifier = Modifier.padding(top = 15.dp).padding(horizontal = 54.dp), fontSize = 17.sp)
                TextField(value = name.value,
                    onValueChange = {newText -> name.value = newText},
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.height(50.dp).width(200.dp)
                )
                Text("Логин", modifier = Modifier.padding(top = 15.dp).padding(horizontal = 18.dp), fontSize = 17.sp)
                TextField(value = login.value,
                    onValueChange = {newText -> login.value = newText},
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.height(50.dp).width(200.dp)
                )
            }
            Row (modifier = Modifier.padding(vertical = 10.dp)) {
                Text("Номер счета", modifier = Modifier.padding(top = 15.dp).padding(horizontal = 18.dp), fontSize = 17.sp)
                TextField(value = acc_num.value,
                    onValueChange = {newText -> acc_num.value = newText},
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.height(50.dp).width(200.dp)
                )
                Text("Пароль", modifier = Modifier.padding(top = 15.dp).padding(horizontal = 10.dp).padding(start = 3.dp), fontSize = 17.sp)
                TextField(value = password.value,
                    onValueChange = {newText -> password.value = newText},
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.height(50.dp).width(200.dp),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Row (modifier = Modifier.padding(vertical = 3.dp)) {
                Text("Повтор пароля", modifier = Modifier.padding(top = 15.dp).padding(horizontal = 18.dp).padding(start = 268.dp), fontSize = 17.sp)
                TextField(value = re_password.value,
                    onValueChange = {newText -> re_password.value = newText},
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.height(50.dp).width(200.dp),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Row (modifier = Modifier.padding(vertical = 10.dp)){
                Button(
                    onClick = {
                        client.start()
                        client.push("reg" + " " + login.value + " " + password.value + " " + name.value + " " + acc_num.value + " " + re_password.value)
                        server_ans = client.pull().toString()
                        client.finish()
                    },
                    modifier = Modifier.padding(start = 270.dp)){
                    Text("Создать аккаунт", fontSize = 17.sp)
                    if(server_ans == "0_reg"){
                        client.login = login.value
                        navController.navigate(Screen.ProfileScreen.name)
                    }
                }
            }
            Row{
                when (server_ans){
                    "1_reg" -> Text("Заполните все поля", modifier = Modifier.padding(start = 283.dp), color = Color.Red)
                    "2_reg" -> Text("Пользователь с таким логином уже существует", modifier = Modifier.padding(start = 160.dp), color = Color.Red)
                    "3_reg" -> Text("Пароли не совпадают", modifier = Modifier.padding(start = 274.dp), color = Color.Red)
                }
            }
        }
    }
}

