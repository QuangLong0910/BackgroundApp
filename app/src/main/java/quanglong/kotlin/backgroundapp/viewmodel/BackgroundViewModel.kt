package quanglong.kotlin.backgroundapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import quanglong.kotlin.backgroundapp.database.repository.BackgroundRepository
import quanglong.kotlin.backgroundapp.model.Background

import java.lang.IllegalArgumentException

class BackgroundViewModel(application: Application) : ViewModel() {
    private val backgroundRepository: BackgroundRepository = BackgroundRepository(application)
    fun insertBackground(background: Background) = viewModelScope.launch {
        backgroundRepository.insertBackground(background)
    }

    fun updateBackground(background: Background) = viewModelScope.launch {
        backgroundRepository.updateBackground(background)
    }

    fun deleteBackground(background: Background) = viewModelScope.launch {
        backgroundRepository.deleteBackground(background)
    }

    fun getAll(): LiveData<List<Background>> = backgroundRepository.getAll()
    class BackgroundViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BackgroundViewModel::class.java)) {
                return BackgroundViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

}