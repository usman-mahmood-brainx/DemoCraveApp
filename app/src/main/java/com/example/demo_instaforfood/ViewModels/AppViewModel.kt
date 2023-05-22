package com.example.demo_instaforfood.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.demo_instaforfood.Repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    val list = repository.getImages().cachedIn(viewModelScope)
}