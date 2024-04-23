import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm() {
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Text(text = "WELCOME TO BOOK HUB")
        Text(text = "Username")
        TextField(value = username, onValueChange = { username = it }, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth())
        Text(text = "Password")
        TextField(value = password, onValueChange = { password = it }, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth())
        Row () {
            Button(
                onClick = { /*TODO*/ },
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

    }
}