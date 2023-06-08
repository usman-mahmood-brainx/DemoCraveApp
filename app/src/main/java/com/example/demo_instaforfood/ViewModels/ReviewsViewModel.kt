package com.example.demo_instaforfood.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.demo_instaforfood.Models.MenuItemRating
import com.example.demo_instaforfood.Repository.AppRepository
import com.example.demo_instaforfood.TemporaryModels.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private val _menuRatingList : MutableLiveData<List<MenuItemRating>> = MutableLiveData()
    val menuRatingList : LiveData<List<MenuItemRating>> = _menuRatingList

    val imagesList:LiveData<PagingData<Data>>
        get() =  repository.getImages().cachedIn(viewModelScope)

    init {
        _menuRatingList.value = dummyMenuRatingList()
    }
    private fun dummyMenuRatingList(): List<MenuItemRating> {
        return listOf(
            MenuItemRating("+9",175),
            MenuItemRating("8",85),
            MenuItemRating("7",22),
            MenuItemRating("6",34),
            MenuItemRating("1 to 5",24)
        )
    }
}