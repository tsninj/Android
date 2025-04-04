package com.example.translator.ui.change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.TranslateTopAppBar
import com.example.translator.data.Word
import com.example.translator.ui.navigation.NavigationDestination

object ChangeDestination : NavigationDestination {
    override val route = "change"
}
@Composable
fun ChangeScreen(
    word: Word? = null,
    onInsertClick: (String, String) -> Unit,
    onCancelClick: () -> Unit
) {
    val viewModel: ChangeViewModel = viewModel(
        factory = ChangeViewModelFactory(word)
    )

    val firstWord by viewModel.firstWord.collectAsState()
    val secondWord by viewModel.secondWord.collectAsState()

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TranslateTopAppBar(
                title = "Change Word",
                onClick = onCancelClick,
                imageVector = Icons.Filled.Close
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Англи -> Монгол",
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 40.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )

            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                modifier = Modifier
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                value = firstWord,
                shape = MaterialTheme.shapes.medium,
                onValueChange = viewModel::updateFirstWord,
                label = { Text("Орчуулах үг") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) }
                )

            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                value = secondWord,
                shape = MaterialTheme.shapes.medium,
                onValueChange = viewModel::updateSecondWord,
                label = { Text("Орчуулсан үг") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onInsertClick(firstWord, secondWord) }
                )
            )

            EditTransButton(
                onInsertClick = { onInsertClick(firstWord, secondWord) },
                onCancelClick = onCancelClick,
            )
        }
    }
}

@Composable
private fun EditTransButton(
    onInsertClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onInsertClick,
            modifier = Modifier.padding(end = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF692FF0),
                contentColor = Color.White
            )
        ) {
            Text(text = "ОРУУЛАХ", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
        OutlinedButton(
            onClick = onCancelClick,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF692FF0),
                contentColor = Color.White
            )
        ) {
            Text(text = "БОЛИХ", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
    }
}
