package com.github.mikan.sample.material3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExpandedDockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mikan.sample.material3.ui.theme.Material3Theme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Material3Theme {
//                TopSearchBar { innerPadding ->
//                    Text("Hello, World!", modifier = Modifier.padding(innerPadding))
//                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    OldSearchBar(
//                        modifier = Modifier
//                            .padding(innerPadding),
//                    )
                    NewSearchBar(
                        modifier = Modifier
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OldSearchBar(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    val items =
        listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew")
    var active by remember { mutableStateOf(false) }
    val filteredItems = items.filter { it.contains(query, ignoreCase = true) }
    DockedSearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { /* Do something */ },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search") },
        leadingIcon = {
            if (active) {
                IconButton({ active = false }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        },
        modifier = modifier,
    ) {
        filteredItems.forEach { item ->
            Text(text = item, fontSize = 15.sp)
        }
    }
//    SearchBar(
//        query = query,
//        onQueryChange = { query = it },
//        onSearch = { /* Do something */ },
//        active = active,
//        onActiveChange = { active = it },
//        placeholder = { Text("Search") },
//        leadingIcon = {
//            if (active) {
//                IconButton({ active = false }) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
//                        contentDescription = null
//                    )
//                }
//            } else {
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = null
//                )
//            }
//        },
//        modifier = modifier,
//    ) {
//        filteredItems.forEach { item ->
//            Text(text = item, fontSize = 15.sp)
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSearchBar(
    modifier: Modifier = Modifier,
) {
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    val items =
        listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew")
    val filteredItems = items.filter { it.contains(textFieldState.text, ignoreCase = true) }
    val inputField = @Composable {
        SearchBarDefaults.InputField(
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
            placeholder = { Text("Search") },
            leadingIcon = {
                if (searchBarState.isExpanded) {
                    IconButton({ scope.launch { searchBarState.animateToCollapsed() } }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
        )
    }

    SearchBar(
        state = searchBarState,
        inputField = inputField,
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxWidth()
            .wrapContentWidth()
    )
    ExpandedDockedSearchBar(
        state = searchBarState,
        inputField = inputField,
    ) {
        filteredItems.forEach { item ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            ) {
                Text(text = item, fontSize = 15.sp)
            }
            HorizontalDivider()
        }
    }
//    ExpandedFullScreenSearchBar(
//        state = searchBarState,
//        inputField = inputField,
//        modifier = Modifier,
//    ) {
//        filteredItems.forEach { item ->
//            Text(text = item, fontSize = 15.sp)
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    val items =
        listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew")
    val filteredItems = items.filter { it.contains(textFieldState.text, ignoreCase = true) }
    val inputField = @Composable {
        SearchBarDefaults.InputField(
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
            placeholder = { Text("Search") },
            leadingIcon = {
                if (searchBarState.isExpanded) {
                    IconButton({ scope.launch { searchBarState.animateToCollapsed() } }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
        )
    }

    Scaffold(
        topBar = {
            TopSearchBar(
                state = searchBarState,
                inputField = inputField,
            )
            ExpandedDockedSearchBar(
                state = searchBarState,
                inputField = inputField,
            ) {
                filteredItems.forEach { item ->
                    Text(text = item, fontSize = 15.sp)
                }
            }
        },
        content = content,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
private val SearchBarState.isExpanded: Boolean
    get() = currentValue == SearchBarValue.Expanded

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NewSearchBarPreview() {
    Material3Theme {
        Surface {
            NewSearchBar()
        }
    }
}
