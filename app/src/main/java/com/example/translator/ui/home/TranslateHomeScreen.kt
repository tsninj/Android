package com.example.translator.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.data.Word
import com.example.translator.data.Option
import com.example.translator.TranslateTopAppBar
import com.example.translator.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
}
@Composable
fun HomeScreen(
    words: List<Word>,
    option: Option,
    onAddClick: () -> Unit,
    onEditClick: (Word) -> Unit,
    onDeleteClick: (Word) -> Unit,
    onSettingsClick: () -> Unit,
    homeViewModel: HomeViewModel = viewModel() // Inject HomeViewModel
) {
    val currentIndex by homeViewModel.currentIndex.collectAsState()
    val currentWord = if (words.isNotEmpty()) words.getOrNull(currentIndex) else null
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TranslateTopAppBar(
                title = "Translate app",
                onClick = onSettingsClick,
                imageVector = Icons.Filled.MoreVert,
            )
        }
    ) { innerPadding ->
        HomeBody(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            currentWord = currentWord,
            option = option,
            onEditClick = onEditClick,
            onAddClick = onAddClick,
            onDeleteClick = onDeleteClick,
            onPreviousClick = { homeViewModel.previousWord(words) },
            onNextClick = { homeViewModel.nextWord(words) },
            words = words
        )
    }
}

@Composable
private fun HomeBody(
    modifier: Modifier = Modifier,
    currentWord: Word?,
    option: Option,
    onEditClick: (Word) -> Unit,
    onAddClick: () -> Unit,
    onDeleteClick: (Word) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    words: List<Word>
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog && currentWord != null) {
        DeleteDialog(
            onConfirmClick = {
                onDeleteClick(currentWord)
                showDialog = false
            },
            onDismissClick = { showDialog = false }
        )
    }
    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 200.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            HomeWordSection(
                currentWord = currentWord,
                option = option,
                onEdit = onEditClick
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(6.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeEditButton(
                onAdd = onAddClick,
                onEdit = { currentWord?.let(onEditClick) },
                onDelete = { showDialog = true },
                isWordAvailable = currentWord != null,
            )
            Spacer(modifier = Modifier.height(10.dp))
            HomeTransButton(
                onPrevious = onPreviousClick,
                onNext = onNextClick,
                isWordsListEmpty = words.isEmpty()
            )
        }
    }
}
@Composable
private fun HomeWordSection(
    currentWord: Word?,
    option: Option,
    onEdit: (Word) -> Unit
) {
    var showMongolia by remember { mutableStateOf(false) }
    var showForeign by remember { mutableStateOf(false) }
    var isHidden by remember { mutableStateOf(false) }

    LaunchedEffect(currentWord, option) {
        showMongolia = false
        showForeign = false
        isHidden = false
    }

    if (currentWord != null) {
        when (option) {
            Option.BOTH -> {
                WordPairDisplay(
                    english = currentWord.english,
                    mongolia = currentWord.mongolia,
                    onEdit = { onEdit(currentWord) }
                )
            }
            Option.ENGLISH -> {
                EnglishWordWithReveal(
                    english = currentWord.english,
                    mongolia = currentWord.mongolia,
                    isRevealed = showMongolia,
                    onReveal = { showMongolia = true },
                    onEdit = { onEdit(currentWord) }
                )
            }
            Option.MONGOLIA -> {
                MongoliaWordWithReveal(
                    english = currentWord.english,
                    mongolia = currentWord.mongolia,
                    isRevealed = showForeign,
                    onReveal = { showForeign = true },
                    onEdit = { onEdit(currentWord) }
                )
            }
        }
    } else {
            WordPairDisplay(
                english = "Англи үг",
                mongolia = "Монгол үг",
                onEdit = {  }
        )
    }
}

@Composable
private fun WordPairDisplay(
    english: String,
    mongolia: String,
    onEdit: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        WordText(
            text = english,
            onClick = {},
            onEdit = onEdit
        )
        Spacer(modifier = Modifier.height(40.dp))
        WordText(
            text = mongolia,
            onClick = {},
            onEdit = onEdit
        )
    }
}

@Composable
private fun EnglishWordWithReveal(
    english: String,
    mongolia: String,
    isRevealed: Boolean,
    onReveal: () -> Unit,
    onEdit: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        WordText(
            text = english,
            onClick = {},
            onEdit = onEdit
        )
        Spacer(modifier = Modifier.height(40.dp))
        if (isRevealed) {
            WordText(
                text = mongolia,
                onClick = {},
                onEdit = onEdit
            )
        } else {
            WordText(
                text = "Энд дарж харна уу",
                onClick = onReveal,
                onEdit = onEdit
            )
        }
    }
}

@Composable
private fun MongoliaWordWithReveal(
    english: String,
    mongolia: String,
    isRevealed: Boolean,
    onReveal: () -> Unit,
    onEdit: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isRevealed) {
            WordText(
                text = english,
                onClick = {},
                onEdit = onEdit
            )
        } else {
            WordText(
                text = "Энд дарж харна уу",
                onClick = onReveal,
                onEdit = onEdit
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        WordText(
            text = mongolia,
            onClick = {},
            onEdit = onEdit
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WordText(
    text: String,
    onClick: () -> Unit,
    onEdit: () -> Unit
) {
    Box(
    modifier = Modifier
        .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
        .background(Color.White)
        .padding(30.dp)
        .fillMaxWidth()
        .combinedClickable(
            onClick = onClick,
            onLongClick = onEdit
        )
)   {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun HomeEditButton(
    onAdd: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    isWordAvailable: Boolean,
) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeButton(text = "НЭМЭХ", onClick = onAdd)
            HomeButton(
                text = "ШИНЭЧЛЭХ",
                onClick = onEdit,
                enabled = isWordAvailable
            )
            HomeButton(
                text = "УСТГАХ",
                onClick = onDelete,
                enabled = isWordAvailable
            )
        }

}
@Composable
private fun HomeTransButton(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    isWordsListEmpty: Boolean
) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeButton(
                text = "ДАРАА",
                onClick = onNext,
                enabled = !isWordsListEmpty
            )
            HomeButton(
                text = "ӨМНӨХ",
                onClick = onPrevious,
                enabled = !isWordsListEmpty
            )
                }
}
@Composable
private fun HomeButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFF692FF0),
            contentColor = Color.White,
    ),
        modifier = Modifier.padding(start= 3.dp)

    ) {
        Text(text=text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = {
            Text(
                text = "Үгээ устгахад итгэлтэй байна уу",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)

            )
        },
        text = {
            Text(
                text = "Устгасны дараа үгээ сэргээх боломжгүй",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismissClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Үгүй")
            }
        },

        confirmButton = {
            Button(
                onClick = onConfirmClick,
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF692FF0)
                )            ) {
                Text("Тийм")
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    )
}