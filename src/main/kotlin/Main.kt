import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import navcontroller.*
import screens.AuthScreen
import screens.ProfileScreen
import screens.RegScreen

@Composable
@Preview
fun App() {

    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.ProfileScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }

    val client = Client()

    MaterialTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                NavigationRail(
                    modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight()
                ){
                    screens.forEach {
                        NavigationRailItem(
                            selected = currentScreen == it.name,
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.label
                                )
                            },
                            label = {
                                    Text(it.label)
                            },
                            alwaysShowLabel = false,
                            onClick = {
                                navController.navigate(it.name)
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    CustomNavigationHost(navController = navController, client)
                }
            }
        }


    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}


/**
 * Screens
 */
enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    ProfileScreen(
        label = "Профиль",
        icon = Icons.Filled.Person
    ),
    AuthScreen(
        label = "Вход",
        icon = Icons.Filled.Home
    ),
    RegScreen(
        label = "Создать аккаунт",
        icon = Icons.Filled.Create
    ),
}


@Composable
fun CustomNavigationHost(
    navController: NavController,
    client: Client
) {
    NavigationHost(navController) {
        composable(Screen.ProfileScreen.name) {
            ProfileScreen(navController, client)
        }

        composable(Screen.AuthScreen.name) {
            AuthScreen(navController, client)
        }

        composable(Screen.RegScreen.name) {
            RegScreen(navController, client)
        }

    }.build()
}