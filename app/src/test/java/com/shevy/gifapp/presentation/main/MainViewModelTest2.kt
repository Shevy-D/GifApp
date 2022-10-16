package com.shevy.gifapp.presentation.main

import com.shevy.gifapp.data.GifsInteractorImpl
import com.shevy.gifapp.domain.interactors.Gif
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

//@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest2 {

/*    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()*/

    @Test
    fun getSearchTextV2() = runBlocking {
        // setup
        val interactorTest = mock<GifsInteractorImpl>()
        val testData = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )
        val text = "Test"
        val result = mock<Deferred<List<Gif>>>()
        `when`(result.await()).thenReturn(testData)
        `when`(interactorTest.getSearchingGifs(text)).thenReturn(result)
        val viewModel = MainViewModel(interactorTest)

        // action
        viewModel.onSearchTextChanged(text = text)

        // check
        val actual = viewModel.searchText.value
        Assertions.assertEquals(text, actual)
    }

    @Test
    suspend fun getSearchText() {
        //Assertions.assertEquals("llo", "Hello".substring(2))

        val interactorTest = mock<GifsInteractorImpl>()

        val testData = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )
        val text = "Test"

        val viewModel = MainViewModel(interactorTest)

        Mockito.`when`(interactorTest.getSearchingGifs(text).await()).thenReturn((testData))

        viewModel.onSearchTextChanged(text = text)

        val actual = viewModel.searchText.value

        //Mockito.verify(interactorTest, Mockito.times(1)).getSearchingGifs(text)
        Assertions.assertEquals(text, actual)

/*        val loading = viewModel.loading
        val searchText: LiveData<String> = viewModel.searchText
        val gifs: LiveData<List<Gif>> = viewModel.gifs*/

/*        val expected = listOf(
            Gif("test preview url", "test url"),
            Gif("test preview url2", "test url2")
        )
        val actual = interactorTest.getSearchingGifs(text).await()*/

/*        Assertions.assertNotNull(actual)
        Assertions.assertEquals(expected, actual)*/

        //assertNotNull(viewModel.onSearchTextChanged(text = text))

        //Assertions.assertNotNull(loading)
    }

    @Test
    fun getLoading() {
    }

    @Test
    fun getGifs() {
    }

    @Test
    fun onSearchTextChanged() {
    }

/*    @Mock
    private lateinit var viewModel: TasksListViewModel

    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    private lateinit var observer: Observer<in Boolean>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = spy(
            TasksListViewModel(
                ApplicationProvider.getApplicationContext(),
                TasksRepositoryImpl(ApplicationProvider.getApplicationContext())
            )
        )
        isLoadingLiveData = viewModel.dataLoading
    }

    @Test
    fun `Verify livedata values changes on event`() {

    assertNotNull(viewModel.getAllTasks())

    viewModel.dataLoading.observeForever(observer)

    verify(observer).onChanged(false)

    viewModel.getAllTasks()

    verify(observer).onChanged(true)

    viewModel.dataLoading.removeObserver(observer)
}

    */
}