package com.example.translator.ui.config

import ConfigViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import com.example.translator.ui.navigation.NavigationDestination

object ConfigDestination : NavigationDestination {
    override val route = "config"
}
@Composable
fun ConfigScreen(
    onBackClick: () -> Unit,
    viewModel: ConfigViewModel = viewModel()
) {
    val selOption by viewModel.selectOption.collectAsState()
    val selectedOptionEnum = Option.fromInt(selOption) // Int-ээс Option руу хөрвүүлэх
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "Харуулах сонголт", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        RadioButtonGroup(
            selectedOption = selectedOptionEnum,
            onOptionSelected = { viewModel.setSelectedOption(it.value) } // Option-оос Int руу хөрвүүлэх
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(onClick = onBackClick) {
                Text(text = "БУЦАХ")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                viewModel.setSelectedOption(selectedOptionEnum.value)
                Toast.makeText(context, "Тохиргоо хадгалагдлаа", Toast.LENGTH_SHORT).show()
                onBackClick()
            }) {
                Text(text = "ХАДГАЛАХ")
            }
        }
    }
}

@Composable
fun RadioButtonGroup(selectedOption: Option, onOptionSelected: (Option) -> Unit) {
    Column {
        RadioButtonItem(Option.FOREIGN, "Гадаад үгийг ил харуулна", selectedOption, onOptionSelected)
        RadioButtonItem(Option.MONGOLIAN, "Монгол үгийг ил харуулна", selectedOption, onOptionSelected)
        RadioButtonItem(Option.BOTH, "Хоёуланг нь ил харуулна", selectedOption, onOptionSelected)
    }
}

@Composable
fun RadioButtonItem(
    value: Option,
    label: String,
    selectedOption: Option,
    onOptionSelected: (Option) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        RadioButton(
            selected = selectedOption == value,
            onClick = { onOptionSelected(value) }
        )
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
    }
}


enum class Option(val value: Int) {
    FOREIGN(1),
    MONGOLIAN(2),
    BOTH(3);

    companion object {
        fun fromInt(value: Int): Option = values().find { it.value == value } ?: FOREIGN
    }
}
