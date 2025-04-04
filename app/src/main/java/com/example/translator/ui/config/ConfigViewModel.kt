import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.data.ConfigRepository
import com.example.translator.ui.config.Option
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConfigViewModel(
    private val configRepository: ConfigRepository
) : ViewModel() {

    val selectOption = configRepository.configLayout
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Option.FOREIGN.value
        )

    fun setSelectedOption(confiLayout: Int) {
        viewModelScope.launch {
            configRepository.saveSettingPreference(confiLayout)
        }
    }
}
