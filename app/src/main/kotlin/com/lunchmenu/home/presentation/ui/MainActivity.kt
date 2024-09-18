package com.lunchmenu.home.presentation.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.lunchmenu.home.data.datasource.remote.HomeRemoteWorker
import com.lunchmenu.home.presentation.viewmodel.HomeViewModel
import com.lunchmenu.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComposeUI()
        startWorkManagerToFetchFromRemote()
        viewModel.loadYelp()
    }

    private fun startWorkManagerToFetchFromRemote() {
        // Fetch From remote
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<HomeRemoteWorker>()
                .build()
        WorkManager
            .getInstance(applicationContext)
            .enqueue(uploadWorkRequest)
    }

    private fun setupComposeUI() {
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel,
                    )
                }
            }
        }
    }
}