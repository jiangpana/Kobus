package com.jansir.sample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.jansir.eventbus.core.observeEvent
import com.jansir.sample.ui.theme.EventBusTheme

class TestViewModelActivity : ComponentActivity() {
    val TAG = javaClass.simpleName

    private val vm by viewModels<TestViewModel> { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvent(String::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Int::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Long::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }
        setContent {
            EventBusTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumn(Modifier.fillMaxSize(), content = {
                        item {

                            Button(onClick = { vm.postStr() }) {
                                Text(text = "post string")
                            }

                            Button(onClick = { vm.postInt() }) {
                                Text(text = "post int")
                            }
                        }


                    })

                }
            }
        }
    }


}

