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
import com.book.myapplication.api.LoginAction
import com.book.myapplication.api.apiService
import com.book.myapplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



@Composable
fun LoginForm() {
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
        mutableStateOf("@@@")
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
                    loginClick = !loginClick
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
        LaunchedEffect(loginClick) {
            var data = User(username, password)
            var res = apiService.login(data)
            if(res.username != null) {
                onLoginResult = "ok Login successfully"
            }else {
                onLoginResult = "Login Failed"
            }

        }
        Text(text = onLoginResult)
    }


}