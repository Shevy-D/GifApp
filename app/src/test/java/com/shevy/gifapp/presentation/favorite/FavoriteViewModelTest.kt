package com.shevy.gifapp.presentation.favorite

import android.app.Application
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.mock

class FavoriteViewModelTest {

/*    fun getAllFavorites(): LiveData<List<Favorite>> {
        return repository.allFavorites
    }*/

/*    val contextTest = mock<Context>()
    val daoNoteTest = FavoriteDatabase.getInstance(contextTest).getFavoriteDao()
    val repositoryText = FavoriteRepositoryImpl(daoNoteTest)*/

    @Test
    fun `get all favorites test`() {
        // setup
        val application = mock<Application>()
        val favoriteViewModelTest = FavoriteViewModel(application)


        //action
        favoriteViewModelTest.initDatabase()
        val actual = favoriteViewModelTest.getAllFavorites()

        val expected = ""

        //check
        Assertions.assertEquals(expected, actual)


    }
}