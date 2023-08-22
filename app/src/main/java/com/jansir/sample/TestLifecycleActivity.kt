package com.jansir.sample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.jansir.eventbus.Kobus
import com.jansir.eventbus.core.observeEvent
import com.jansir.sample.ui.theme.EventBusTheme

class TestLifecycleActivity : ComponentActivity() {
    val TAG = javaClass.simpleName

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

                            Button(onClick = { postStr() }) {
                                Text(text = "post string")
                            }

                            Button(onClick = { postInt() }) {
                                Text(text = "post int")
                            }
                        }


                    })

                }
            }
        }
    }

    private fun postInt() {
        Kobus.post((1..1000).random())

    }

    private fun postStr() {
        Kobus.post("发送str")
    }



}

