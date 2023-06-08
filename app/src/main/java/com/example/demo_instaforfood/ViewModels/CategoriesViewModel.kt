package com.example.demo_instaforfood.ViewModels

import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.demo_instaforfood.Models.Category
import com.example.demo_instaforfood.Models.MenuItemRating
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.Repository.AppRepository
import com.example.demo_instaforfood.TemporaryModels.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel  @Inject constructor() : ViewModel() {
    private val _categoryList : MutableLiveData<List<Category>> = MutableLiveData()
    val categoryList : LiveData<List<Category>> = _categoryList


    init {
        _categoryList.value = dummylist()
    }
    private fun dummylist(): List<Category> {
        return listOf(
            Category("Brunch", 460, "#5499CB"),
            Category("Burgers", 61, "#E27E46"),
            Category("Pizza", 46,"#C23E38"),
            Category("Bangels", 28,"#6EB895"),
            Category("Tacos", 19, "#597896"),
            Category("Donuts", 17,"#6DB9C7"),
            Category("Coffee", 13, "#F1B65A"),
            Category("Bar", 10,"#F1B65A")
        )
    }
}