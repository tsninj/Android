package com.example.translator.ui.change

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.ui.navigation.NavigationDestination

object ChangeDestination : NavigationDestination {
    override val route = "ug-nemeh"
}
@Composable
fun ChangeScreen(  isEditing: Boolean,
                   itemId: Int?,
                   onCancelClick: () -> Unit,
                   onInsertClick: () -> Unit,
                    viewModel: ChangeViewModel = viewModel()) {
    val firstWord by viewModel.firstWord.collectAsState()
    val secondWord by viewModel.secondWord.collectAsState()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Үг өөрчлөх",
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            label = { Text("Орчуулах үг") },
            value = firstWord,
            onValueChange = { viewModel.onFirstWordChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            label = { Text("Орчуулсан үг") },
            value = secondWord,
            onValueChange = { viewModel.onSecondWordChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        EditTransButton(
            onInsertClick = { viewModel.insertTranslation() },
            onCancelClick = { viewModel.cancelTranslation() }
        )
    }
    if (isEditing) {
        Text("Editing item with ID: $itemId")
    } else {
        Text("Adding new item")
    }

}

@Composable
private fun EditTransButton(
    onInsertClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onInsertClick,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(text = "ОРУУЛАХ", color = Color(0xff692ff0))
        }
        OutlinedButton(
            onClick = onCancelClick
        ) {
            Text(text = "БОЛИХ", color = Color(0xff692ff0))
        }
    }
}
