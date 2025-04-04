package com.example.translator.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.InventoryTopAppBar
import com.example.translator.ui.navigation.NavigationDestination
import com.example.translator.ui.theme.InventoryTheme
import com.example.translator.ui.home.HomeViewModel
import com.example.translator.data.Word
import kotlinx.coroutines.flow.collectLatest
import com.example.translator.ui.config.Option

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
    option: Option
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

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
            contentPadding = innerPadding,
            words : List<Word>,
            onAddClick = navigateToItemEntry,
            onUpdateClick = { navigateToItemUpdate(uiState.currentWord?.id ?: 0) },
            onDeleteClick = { viewModel.confirmDelete() },
            onPrevClick = { viewModel.showPreviousWord() },
            onNextClick = { viewModel.showNextWord() },
            onRevealMongolian = { viewModel.toggleTranslationVisibility() },
            onLongPressWord = { navigateToItemUpdate(uiState.currentWord?.id ?: 0) },
            onConfirmDelete = { viewModel.deleteCurrentWord() },
            onDismissDeleteDialog = { viewModel.dismissDeleteDialog() }
        )
    }
}

@Composable
fun HomeBody(
    uiState: UiState,
    onAddClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onRevealMongolian: () -> Unit,
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

        if(currentWord != null){
            when(option){
                Option.BOTH -> HomeWordSection(
                    uiState = uiState,
                    onRevealMongolian = onRevealMongolian,
                    onLongPressWord = onLongPressWord,
                    modifier = Modifier.weight(1f)
            }
        }

        HomeEditButton(
            onAddClick = onAddClick,
            onUpdateClick = onUpdateClick,
            onDeleteClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = uiState.hasWords
        )

        HomeTransButton(
            onPrevClick = onPrevClick,
            onNextClick = onNextClick,
            modifier = Modifier.padding(16.dp),
            enabled = uiState.hasWords
        )
    }

    if (uiState.showDeleteDialog) {
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
    uiState: UiState,
    onRevealMongolian: () -> Unit,
    onLongPressWord: () -> Unit,
    modifier: Modifier = Modifier
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
        ) {
            Text(
                text = "${uiState.currentWord?.english ?: ""}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 36.sp
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
        modifier = modifier, horizontalArrangement = Arrangement.Center
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
