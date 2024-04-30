package com.book.myapplication.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.VM.BookVM
import com.book.myapplication.VM.UserVM
import com.book.myapplication.model.Book


@Composable
fun ImageFromLocalhostUrl(
    book : Book,
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
    Box(modifier = Modifier.fillMaxWidth().clickable { onBookClick(book) }) {
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
    book : Book,
    onBookClick: (Book) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)

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
fun BookList(book_vm : BookVM, onBookClick: (Book) -> Unit) {
    Column {
        var ListBooks = (book_vm.LoadListBooks() ?: emptyList()).toMutableList()

        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(ListBooks) { item ->
                StoryCard(item, onBookClick)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSample() {
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
            onSearch = { active = false },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
        ) {}

    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUi(navController: NavController, data: UserVM) {
    val res = data.data.value // handle data receive
    var ListBooks = rememberSaveable {
        mutableListOf<Book>()
    }
    var bookData = rememberSaveable {
        mutableStateOf<Book?>(null)
    }
    val book_vm = viewModel<BookVM>()
    val themeIcons = Modifier.size(100.dp)
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(16,52,166),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                modifier = Modifier.fillMaxWidth().size(65.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    contentColor = Color.White,
                    color = Color(16,52,166),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            Log.i("resultAPI", "Home")
                        }) {
                            Icon(Icons.Filled.Home, contentDescription = null, modifier = themeIcons)
                        }
                        IconButton(onClick = {
                            Log.i("resultAPI", "List Follow")
                        }) {
                            Icon(Icons.Default.Favorite, "", modifier = themeIcons)
                        }
                        IconButton(onClick = {
                            Log.i("resultAPI", "Search")
                        }) {
                            Icon(Icons.Default.Search, "", modifier = themeIcons)
                        }
                        IconButton(onClick = {
                            Log.i("resultAPI", "ProfileI")
                        }) {
                            Icon(Icons.Default.AccountBox, "", modifier = themeIcons)
                        }
                    }
                }


            }
        },

        ) {innerPadding  ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SearchBarSample()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Text(text = "Top favorite stories of the week")
                BookListHorizon() {book ->
                    Log.i("resultAPI", "ok1")
                }
                Text(text = "List stories")
                BookList(book_vm = book_vm) {book ->
                    navController.navigate("about-book/${book.book_id}")
                }
            }
        }
    }


}