package com.posite.modern.ui.wish

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.local.entity.WishEntity
import com.posite.modern.data.repository.wish.WishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishViewModelImpl @Inject constructor(private val repository: WishRepository) : ViewModel(),
    WishViewModel {
    private val _wishTitle = mutableStateOf("")
    override val wishTitle: State<String>
        get() = _wishTitle

    private val _wishDescription = mutableStateOf("")
    override val wishDescription: State<String>
        get() = _wishDescription

    private val _wishes = mutableStateOf(emptyList<WishEntity>())
    override val wishes: State<List<WishEntity>>
        get() = _wishes

    private val _currentWish = mutableStateOf<WishEntity>(WishEntity.getEmpty())
    override val currentWish: State<WishEntity>
        get() = _currentWish


    override fun wishTitleChanged(title: String) {
        _wishTitle.value = title
    }

    override fun wishDescriptionChanged(description: String) {
        _wishDescription.value = description
    }

    override fun addWish(wish: WishEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWish(wish)
        }
    }

    override fun getWishes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWishes().collect {
                _wishes.value = it
            }
        }
    }

    override fun getWishById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWishById(id).collect {
                _currentWish.value = it
            }
        }
    }

    override fun updateWish(wish: WishEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWish(wish)
        }
    }

    override fun deleteWish(wish: WishEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWish(wish)
        }
    }
}