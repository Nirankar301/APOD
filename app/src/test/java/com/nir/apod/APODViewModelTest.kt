package com.nir.apod

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.nir.apod.data.IAPODRepository
import com.nir.apod.model.APOD
import com.nir.apod.viewmodel.APODViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class APODViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var repository: IAPODRepository
    private lateinit var viewModel: APODViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = Mockito.mock(IAPODRepository::class.java)
        viewModel = APODViewModel(coroutineRule.dispatcher, repository)
    }

    @Test
    fun getAPODSuccess() = coroutineRule.runBlockingTest {
        whenever(repository.getAPOD()).thenReturn(APOD("test", "2021-09-25", "description", "", "", "", "", ""))
        viewModel.getAPOD()
        Assert.assertNotNull(viewModel.apodLiveData.value)
    }

    @Test
    fun getAPODFailure() = coroutineRule.runBlockingTest {
        whenever(repository.getAPOD()).thenReturn(null)
        viewModel.getAPOD()
        Assert.assertNull(viewModel.apodLiveData.value)
    }
}