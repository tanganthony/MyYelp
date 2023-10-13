package com.tanganthony.myyelp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tanganthony.myyelp.ui.MainScreen
import com.tanganthony.myyelp.ui.appbar.ViewModel
import com.tanganthony.myyelp.ui.MyYelpTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyYelpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: ViewModel = viewModel()
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}
