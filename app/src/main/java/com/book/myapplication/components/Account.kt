package com.book.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.R
import com.book.myapplication.ViewModel.HistoryVM
import com.book.myapplication.ViewModel.UserVM
import com.book.myapplication.model.History
import com.book.myapplication.model.User
import kotlinx.coroutines.launch

@Composable
fun AccountInformation(modifier: Modifier = Modifier, username: String, follow: Int) {
    Row(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .size(100.dp) // Size of the circle (diameter)
                .background(Color.White, shape = CircleShape)
                .border(1.dp, Color.Green, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            //image here
            Text(text = "aBCDE")
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier.padding(start = 4.dp, top = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = username, fontSize = 25.sp)
            Text(text = "$follow Follows")
        }
    }
}

@Composable
fun MarkCard(iconResource: Int, content: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = iconResource),
            "",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = content)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, "")
    }
}

@Composable
fun ImageHandleHistory(
    book: History,
    onBookClick: (History) -> Unit,
) {
    val url: String = "http://10.0.2.2:8080/Books/${book.book_name}/image.png"
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // You can customize image loading parameters here
                }).build()
        )
    Box(modifier = Modifier
//        .fillMaxWidth()
        .clickable { onBookClick(book) }) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(25.dp))
        )
    }
}

@Composable
fun HistoryCard(
    book: History,
    onBookClick: (History) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        ImageHandleHistory(book, onBookClick)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (book.book_name.length > 10) {
                "${book.book_name.take(10)}..."
            } else {
                book.book_name
            }, style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
fun MoreHistoryCard(navController: NavController) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(130.dp)
            .background(Color(3, 155, 247))
            .clickable {
                navController.navigate("history")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .border(1.dp, Color.White, shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.ArrowForward, "backToMain",
                    modifier = Modifier
                        .size(45.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "View more", color = Color.White, fontWeight = FontWeight(600))
        }
    }
}


@Composable
fun HistoryViewStory(
    modifier: Modifier = Modifier,
    navController: NavController,
    books: List<History>
) {
    Box(
        modifier = modifier
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp)
                .background(color = Color.White)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "History", fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, "",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                navController.navigate("history")
                            })
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(180.dp)
                ) {
                    LazyRow {
                        items(books) { item ->
                            HistoryCard(book = item) { book ->
                                navController.navigate("about-book/${item.book_id}")
                            }
                        }
                        item {
                            MoreHistoryCard(navController)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                MarkCard(R.drawable.downloadicon, "Downloaded")
                Spacer(modifier = Modifier.height(15.dp))
                MarkCard(R.drawable.bookmark, "Favorite List")
            }
        }

    }
}

@Composable
fun StoryAndPremium(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
                .widthIn(0.dp)
                .background(Color.White, shape = RoundedCornerShape(20))
                .padding(8.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.upload), contentDescription = "",
                        modifier = Modifier.size(35.dp),
                        tint = Color.Green
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "Post stories", fontSize = 20.sp, fontWeight = FontWeight(600))
                    Text(text = "post your favorite stories", fontWeight = FontWeight(300))
                }
            }
        }
        Spacer(
            modifier = Modifier
                .width(10.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
                .widthIn(0.dp)
                .background(Color.White, shape = RoundedCornerShape(20))
                .padding(8.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gem), contentDescription = "",
                        modifier = Modifier.size(35.dp), tint = Color(243, 53, 115)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Text(text = "My premium", fontSize = 20.sp, fontWeight = FontWeight(600))
                    Text(text = "More recharge,", fontWeight = FontWeight(300))
                    Text(text = "More discounts", fontWeight = FontWeight(300))
                }
            }
        }
    }
}

@Composable
fun OtherList(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dataUserStore = UserData(context)
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        MarkCard(R.drawable.shop, "Shop")
        Spacer(modifier = Modifier.height(15.dp))
        MarkCard(R.drawable.wallet_solid, "Wallet")
        Spacer(modifier = Modifier.height(15.dp))
        MarkCard(R.drawable.event, "Event")
        Spacer(modifier = Modifier.height(15.dp))
        MarkCard(R.drawable.setting, "Setting")
        Spacer(modifier = Modifier.height(15.dp))
        MarkCard(R.drawable.help, "Help")
        Spacer(modifier = Modifier.height(15.dp))
        MarkCard(R.drawable.feedback, "Feedback")
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(30.dp)
                .clickable {
                    scope.launch {
                        dataUserStore.SetDataUserInLocal(null)
                        navController.navigate("main")
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.logout),
                "",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Log out", color = Color.Red)
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}

@Composable
fun AboutAccount(navController: NavController) {

    val context = LocalContext.current
    val dataUserStore = UserData(context)
    val dataUser =
        dataUserStore.getDataUserFromLocal.collectAsState(initial = User(0, "", ""))
    var idUser by rememberSaveable {
        mutableIntStateOf(0)
    }
    idUser = dataUser.value?.user_id ?: 0
    val history_vm: HistoryVM = viewModel()
    history_vm.loadTopTenHistory(idUser.toString())

    val dataFromHistory by history_vm.listTopTenHistory.collectAsState()

    val user_vm : UserVM = viewModel()
    if(idUser != 0) {
        user_vm.loadUserInfo(idUser.toString())
    }
    val username by user_vm.userInfo.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(245, 245, 245))
    ) {
        item {
            Icon(
                Icons.Filled.ArrowBack, "backToMain",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.navigate("main")
                    }
            )
            AccountInformation(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.1.dp, Color.White)
                    .background(color = Color.White), username.username.toString() ?: "Guest", 100
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(245, 221, 234), shape = RoundedCornerShape(10)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Don't have premium ?",
                        fontSize = 15.sp,
                        color = Color(241, 51, 113),
                        fontWeight = FontWeight(600)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(width = 50.dp, height = 30.dp)
                            .background(Color(241, 51, 113), shape = RoundedCornerShape(30)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "BUY", color = Color.White, fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HistoryViewStory(modifier = Modifier.fillMaxWidth(), navController, dataFromHistory)
            Spacer(modifier = Modifier.height(10.dp))
            StoryAndPremium()
            Spacer(modifier = Modifier.height(10.dp))
            OtherList(navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutAccountPreView(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    AboutAccount(navController)
}


