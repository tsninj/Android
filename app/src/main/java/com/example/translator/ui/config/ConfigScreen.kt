 package com.example.translator.ui.config

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.translator.TranslateTopAppBar
import com.example.translator.data.Option
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import com.example.translator.ui.navigation.NavigationDestination

 object ConfigDestination : NavigationDestination {
     override val route = "config"
 }
 @Composable
 fun ConfigScreen(
     viewModel: ConfigViewModel,
     onCancelClick: () -> Unit,
     onSaveClick: () -> Unit
 ) {
     val currentConfig by viewModel.configLayout.collectAsState()

     Scaffold(
         topBar = {
             TranslateTopAppBar(
                 imageVector = Icons.Filled.Close,
                 title = "Configuration",
                 onClick = onCancelClick,
             )
         }
     ) { innerValues ->
         val scrollState = rememberScrollState()
         Column(
             modifier = Modifier
                 .verticalScroll(scrollState)
                 .padding(innerValues)
                 .padding(16.dp)
         ) {
             Text(
                 text = "Хэлний сонголт",
                 modifier = Modifier
                     .padding(bottom = 16.dp, top = 40.dp)
                     .align(Alignment.CenterHorizontally),
                 fontSize = MaterialTheme.typography.headlineMedium.fontSize
             )

             Spacer(modifier = Modifier.height(20.dp))

             RadioButtonGroup(
                 selectedOption = currentConfig,
                 onOptionSelected = { newOption ->
                     viewModel.saveOption(newOption)
                 }
             )

             Spacer(modifier = Modifier.height(20.dp))

             EditTransButton(
                 onInsertClick = onSaveClick,
                 onCancelClick = onCancelClick
             )
         }
     }
 }

@Composable
fun RadioButtonGroup(
    selectedOption: Option,
    onOptionSelected: (Option) -> Unit
) {
    Column {
        RadioButtonItem(
            option = Option.ENGLISH,
            text = "Гадаад үгийг ил харуулна",
            isSelected = selectedOption == Option.ENGLISH,
            onClick = { onOptionSelected(Option.ENGLISH) }
        )
        RadioButtonItem(
            option = Option.MONGOLIA,
            text = "Монгол үгийг ил харуулна",
            isSelected = selectedOption == Option.MONGOLIA,
            onClick = { onOptionSelected(Option.MONGOLIA) }
        )
        RadioButtonItem(
            option = Option.BOTH,
            text = "Хоёуланг нь ил харуулна",
            isSelected = selectedOption == Option.BOTH,
            onClick = { onOptionSelected(Option.BOTH) }
        )
    }
}

@Composable
private fun RadioButtonItem(
    option: Option,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Text(text = text, modifier = Modifier.padding(start = 8.dp))
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
            onClick = onCancelClick,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF692FF0),
                contentColor = Color.White
            )
        ) {
            Text(text = "БУЦАХ",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold)
        }
        OutlinedButton(
            onClick = onInsertClick,
            modifier = Modifier.padding(end = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF692FF0),
                contentColor = Color.White
            )
        ) {
            Text(text = "ХАДГАЛАХ",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold)
        }

    }
}