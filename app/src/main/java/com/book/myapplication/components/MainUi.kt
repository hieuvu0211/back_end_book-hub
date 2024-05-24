package com.book.myapplication.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.R
import com.book.myapplication.ViewModel.BookVM
import com.book.myapplication.model.Book
import com.book.myapplication.model.User


@Composable
fun ImageFromLocalhostUrl(
    book: Book,
    onBookClick: (Book) -> Unit,
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
fun StoryCard(
    book: Book,
    onBookClick: (Book) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
//            .clip(MaterialTheme.shapes.medium)
//            .background(MaterialTheme.colorScheme.surface)

//            .padding(8.dp)
    ) {
        ImageFromLocalhostUrl(book, onBookClick)
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

//render List Book data
@Composable
fun BookList(list_books: List<Book>, book_vm: BookVM, onBookClick: (Book) -> Unit) {
    val sizeListBook = list_books.size / 2
    Column {
        for (i in 0..sizeListBook - 1 step 3) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
            ) {
                for (j in i..i + 3) {
                    if(j <= sizeListBook) {
                        StoryCard(list_books[j], onBookClick)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSample(navController: NavController) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                active = false
                if (text != "") {
                    navController.navigate("result-search/$text")
                }
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
        ) {}

    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUi(navController: NavController) {
//    val res = data.data.value // handle data receive
    val book_vm = viewModel<BookVM>()
    val listBooks = (book_vm.LoadListBooks() ?: emptyList()).toMutableList()
    val themeIcons = Modifier.size(100.dp)
    val context = LocalContext.current
    val dataUserStore = UserData(context)
    val getDataUserFromLocal =
        dataUserStore.getDataUserFromLocal.collectAsState(initial = User(0, "", ""))
    var idUser by rememberSaveable {
        mutableIntStateOf(0)
    }
    idUser = getDataUserFromLocal.value?.user_id ?: 0

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(40.dp)
                    .border(0.1.dp, Color(202, 247, 183))
            ) {
                Surface(
                    shape = CircleShape,
                    contentColor = Color.White,
                    color = Color.White,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            Log.i("resultAPI", "Home")
                            navController.navigate("main")
                        }) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = null,
                                modifier = themeIcons,
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = {
                            Log.i("resultAPI", "List Follow")
                            navController.navigate("follow/${idUser}")
                        }) {
                            Icon(
                                Icons.Default.Favorite,
                                "",
                                modifier = themeIcons,
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = {
                            Log.i("resultAPI", "Search")
                        }) {
                            Icon(
                                Icons.Default.Search,
                                "",
                                modifier = themeIcons,
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = {
                            if (getDataUserFromLocal.value != null) {
                                navController.navigate("account")
                            } else navController.navigate("login")
                        }) {
                            Icon(
                                Icons.Default.AccountBox,
                                "",
                                modifier = themeIcons,
                                tint = Color.Black
                            )

                        }
                    }
                }


            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color(245, 245, 245)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SearchBarSample(navController)
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .background(color = Color(245, 245, 245)),
            ) {
                item {
                    Text(text = stringResource(id = R.string.title_topfavorite))
                    BookListHorizon(book_vm) { book ->
                        navController.navigate("about-book/${book.book_id}")
                    }
                    Text(text = "List stories")
//                        BookList(listBooks,book_vm = book_vm) {book ->
//                            navController.navigate("about-book/${book.book_id}")
//                        }
                    BookList(listBooks, book_vm) {book ->
                        navController.navigate("about-book/${book.book_id}")
                    }

                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ShowMainUiPreview(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    MainUi(navController = navController)
}