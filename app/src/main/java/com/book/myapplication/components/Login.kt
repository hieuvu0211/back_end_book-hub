
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.R
import com.book.myapplication.VM.UserVM
import com.book.myapplication.api.userService
import com.book.myapplication.model.UserLogin
import kotlinx.coroutines.launch


@Composable
fun LoginForm(navController: NavController, viewModel: UserVM) {
    val imageLogoModifier = Modifier
        .size(200.dp)
        .border(BorderStroke(1.dp, Color.White))
        .fillMaxWidth()
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var loginClick by rememberSaveable {
        mutableStateOf(false)
    }
    var onLoginResult by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val dataUserStore = UserData(context)
    val scope = rememberCoroutineScope()



    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(
            Icons.Filled.ArrowBack, "backToMain",
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .clickable {
                    navController.navigate("main")
                }
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.booklogo),
                contentDescription = "Logo",
                modifier = imageLogoModifier,
            )
            Text(
                text = "Login to Your Account",
                fontSize = 27.sp,
                fontWeight = FontWeight(700)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .border(2.dp, Color.White)
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var colUsername by remember {
                        mutableStateOf(Color.White)
                    }
                    Icon(Icons.Filled.AccountCircle, "account")
                    TextField(value = username, onValueChange = { username = it },
                        modifier = Modifier
//                            .padding(16.dp)
                            .onFocusChanged {
                                colUsername = if (it.isFocused) Color.Green else Color.White
                            }
                            .border(2.dp, colUsername)
                            .fillMaxWidth(), label = {
                            Text("username")
                        })
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .border(2.dp, Color.White)
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var colPassword by remember {
                        mutableStateOf(Color.White)
                    }
                    Icon(Icons.Filled.Lock, "account")
                    TextField(
                        value = password, onValueChange = { password = it },

                        modifier = Modifier
                            .background(Color.White)
                            .onFocusChanged {
                                colPassword = if (it.isFocused) Color.Green else Color.White
                            }
                            .border(2.dp, colPassword)
                            .fillMaxWidth(),
                        label = { Text("password") },
                        visualTransformation = VisualTransformation.None
                    )

                }
            }
            Button(
                onClick = {
                    loginClick = true
                    Log.i("resultAPI", "onclicked")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(8, 185, 65),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp)
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
            if(loginClick) {
                Log.i("resultAPI", "onclicked1")
                LaunchedEffect(loginClick) {
                    try {
                        var data = UserLogin(username, password)
                        val res = userService.login(data)
                        Log.i("resultAPI", "data = $res")
                        if(res.username != null) {
                            viewModel.setData(res)
                            scope.launch {
                                dataUserStore.SetDataUserInLocal(res)
                            }
                            navController.navigate("main")
                        }else {
                            onLoginResult = true
                        }
                    }catch (e: Error) {
                        Log.i("resultAPI", "$e")
                    }
                }
            }
            Text(
                text = "Forgot the password ?", color = Color(114, 206, 150),
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "or continue with",
                fontWeight = FontWeight(400),
                modifier = Modifier.padding(top = 32.dp)
            )
            Box(
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook1),
                        contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.google), contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gihub), contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 32.dp)) {
                Text(text = "don't have an account?   ")
                Text(text = "Register", color = Color.Green, fontWeight = FontWeight(700))
            }

        }

    }
}
