package com.hamza.wallpap.data.local.dao

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hamza.wallpap.data.local.UnsplashDatabase
import com.hamza.wallpap.data.repository.FavUrlsRepository
import com.hamza.wallpap.model.FavouriteUrls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavUrlsViewModel(
    application: Application
) : AndroidViewModel(application) {

    var clearAllImagesDialogState = mutableStateOf(false)
    private val readAllFavUrls: LiveData<List<FavouriteUrls>>
    private val unsplashImageDao = UnsplashDatabase.getDatabase(application).unsplashImageDao()
    private val repository = FavUrlsRepository(unsplashImageDao)
    val getAllFavUrls = repository.readAllFavUrls

//    var alreadyInFavorite = mutableStateOf(false)

    init {
        readAllFavUrls = repository.readAllFavUrls
    }

//    fun addToFav(favouriteUrls: FavouriteUrls) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addFavUrl(favouriteUrls)
//        }
//    }

    fun addToFav(favouriteUrls: FavouriteUrls) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingFavUrl = repository.getFavouriteUrlById(favouriteUrls.id)
            if (existingFavUrl == null) {
//                alreadyInFavorite.value = true
                repository.addFavUrl(favouriteUrls)
                // Display a message to the user that the image has been added to their favorites

            } else {
                // Display a message to the user that the image is already in their favorites
//                alreadyInFavorite.value = false
            }
        }
    }

//    fun getFavUrl(favouriteUrls: FavouriteUrls){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getFavUrl(favouriteUrls)
//        }
//    }

    fun deleteFavouriteUrl(favouriteUrls: FavouriteUrls) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavUrl(favouriteUrls)
        }
    }

    fun deleteAllFavouriteUrls(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavUrls()
        }
    }
}