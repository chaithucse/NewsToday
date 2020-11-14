package com.techchai.newstoday

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.techchai.newstoday.data.NewsRepository
import com.techchai.newstoday.viewmodel.NewsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @Rule
    var instantExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @Mock
    lateinit var viewModel: NewsViewModel

    @Mock
    lateinit var repository: NewsRepository

    @Before
    fun setup() {
        viewModel = NewsViewModel(ApplicationProvider.getApplicationContext());
        repository = NewsRepository(ApplicationProvider.getApplicationContext())
    }
}