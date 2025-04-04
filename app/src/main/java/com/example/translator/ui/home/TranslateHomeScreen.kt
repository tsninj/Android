package com.example.translator.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.InventoryTopAppBar
import com.example.translator.data.Word
import com.example.translator.ui.config.Option
import com.example.translator.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "nuur-huudas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    navigateToConfig: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    words: List<Word>,
    currentIndex: Int,
    option: Option
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val currentWord = words.getOrNull(currentIndex)
    var showDeleteDialog by remember { mutableStateOf(false) }
    val hasWords = words.isNotEmpty()

    // Toggle for revealing the hidden language
    var isHiddenRevealed by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            InventoryTopAppBar(
                title = "Translate app",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onShareButtonClicked = navigateToConfig,
            )
        }
    ) { innerPadding ->
        HomeBody(
            currentWord = currentWord,
            showDeleteDialog = showDeleteDialog,
            hasWords = hasWords,
            contentPadding = innerPadding,
            option = option,
            isHiddenRevealed = isHiddenRevealed,
            onAddClick = navigateToItemEntry,
            onUpdate = { currentWord?.let { navigateToItemUpdate(it.id) } },
            onDeleteClick = { showDeleteDialog = true },
            onPrevClick = { viewModel.showPreviousWord() },
            onNextClick = { viewModel.showNextWord() },
            onRevealHidden = { isHiddenRevealed = !isHiddenRevealed },
            onLongPressWord = { currentWord?.let { navigateToItemUpdate(it.id) } },
            onConfirmDelete = {
                viewModel.deleteCurrentWord()
                showDeleteDialog = false
            },
            onDismissDeleteDialog = { showDeleteDialog = false }
        )
    }
}

@Composable
fun HomeBody(
    currentWord: Word?,
    showDeleteDialog: Boolean,
    hasWords: Boolean,
    option: Option,
    isHiddenRevealed: Boolean,
    onAddClick: () -> Unit,
    onUpdate: (Word) -> Unit,
    onDeleteClick: () -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onRevealHidden: () -> Unit,
    onLongPressWord: () -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDeleteDialog: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(80.dp))

        currentWord?.let {
            when (option) {
                Option.FOREIGN -> {
                    HomeWordSection(
                        text = it.english,
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        onLongPressWord = { onUpdate(it) }
                    )
                    HomeWordSection(
                        text = if (isHiddenRevealed) it.mongolia else "Дарна уу",
                        modifier = Modifier.weight(1f),
                        onClick = { if (!isHiddenRevealed) isHiddenRevealed = true },
                        onLongPressWord = { onUpdate(it) }
                    )
                }
                Option.MONGOLIAN -> {
                    HomeWordSection(
                        text = it.mongolia,
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        onLongPressWord = { onUpdate(it) }
                    )
                    HomeWordSection(
                        text = if (isHiddenRevealed) it.english else "Дарна уу",
                        modifier = Modifier.weight(1f),
                        onClick = { if (!isHiddenRevealed) isHiddenRevealed = true },
                        onLongPressWord = { onUpdate(it) }
                    )
                }
                Option.BOTH -> {
                    HomeWordSection(
                        text = it.english,
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        onLongPressWord = { onUpdate(it) }
                    )
                    HomeWordSection(
                        text = it.mongolia,
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        onLongPressWord = { onUpdate(it) }
                    )
                }
            }
        } ?: HomeWordSection(
            text = "Үг байхгүй байна.",
            modifier = Modifier.weight(1f),
            onClick = {},
            onLongPressWord = {}
        )

        Button(
            onClick = onRevealHidden,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)
        ) {
            Text("Орчуулгыг харуулах")
        }

        HomeEditButton(
            onAddClick = onAddClick,
            onUpdateClick = { currentWord?.let(onUpdate) },
            onDeleteClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = hasWords
        )

        HomeTransButton(
            onPrevClick = onPrevClick,
            onNextClick = onNextClick,
            modifier = Modifier.padding(16.dp),
            enabled = hasWords
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = onDismissDeleteDialog,
            confirmButton = {
                TextButton(onClick = onConfirmDelete) { Text("УСТГАХ") }
            },
            dismissButton = {
                TextButton(onClick = onDismissDeleteDialog) { Text("Болих") }
            },
            title = { Text("Та энэ үгийг устгахдаа итгэлтэй байна уу?") }
        )
    }
}

@Composable
fun HomeWordSection(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit = {},
    onLongPressWord: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongPressWord
                )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 36.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(45.dp))
    }
}

@Composable
fun HomeEditButton(
    onAddClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onAddClick,
            shape = RoundedCornerShape(2.dp),
            border = BorderStroke(2.dp, Color(0xff692ff0))
        ) {
            Text(text = "НЭМЭХ", color = Color.White)
        }
        Button(
            onClick = onUpdateClick,
            shape = RoundedCornerShape(2.dp),
            border = BorderStroke(2.dp, Color(0xff692ff0)),
            enabled = enabled,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "ШИНЭЧЛЭХ", color = Color.White)
        }
        Button(
            onClick = onDeleteClick,
            shape = RoundedCornerShape(2.dp),
            border = BorderStroke(2.dp, Color(0xff692ff0)),
            enabled = enabled,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "УСТГАХ", color = Color.White)
        }
    }
}

@Composable
fun HomeTransButton(
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onPrevClick,
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(2.dp, Color(0xff692ff0)),
            enabled = enabled
        ) {
            Text(text = "ӨМНӨХ", color = Color.White)
        }
        Spacer(modifier = Modifier.width(30.dp))
        Button(
            onClick = onNextClick,
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(2.dp, Color(0xff692ff0)),
            enabled = enabled
        ) {
            Text(text = "ДАРАА", color = Color.White)
        }
    }
}
