import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.book.myapplication.VM.UserVM
import com.book.myapplication.api.apiService
import com.book.myapplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



@Composable
fun LoginForm(navController: NavController, viewModel: UserVM) {
    var username by rememberSaveable {
        mutableStateOf("user1")
    }
    var password by rememberSaveable {
        mutableStateOf("password666")
    }
    var loginClick by rememberSaveable {
        mutableStateOf(false)
    }
    var onLoginResult by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "WELCOME TO BOOK HUB")
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Row() {
            Button(
                onClick = {
                    loginClick = true
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Login")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Register")
            }
        }
        if(loginClick) {
            LaunchedEffect(loginClick) {
                try {

                    var data = User(username, password)
                    val res = apiService.login(data)
                    if(res.username != null) {
                        Log.i("eAPI", "${res.username}")
                        viewModel.setData(res)
                        navController.navigate("main/$res")
                    }else {
                        onLoginResult = true
                    }
                }catch (e: Error) {
                    Log.i("eAPI", "$e")
                }
            }
        }

        if(onLoginResult) {
            Text(text = "Login Failed")
        }
    }


}