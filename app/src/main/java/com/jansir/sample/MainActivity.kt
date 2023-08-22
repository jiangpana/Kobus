package com.jansir.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.jansir.eventbus.core.*
import com.jansir.sample.ui.theme.EventBusTheme
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class MainActivity : ComponentActivity() {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvent(String::class.java) {
            launch {
                Log.d(TAG, "observeEvent: 收到$it")
            }
            withContext(Dispatchers.IO) {

            }
            async {

            }
        }.observeEvent(Int::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Long::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(TestEvent::class.java) {
            Log.d(TAG, "observeEvent: 收到${it.message}")
        }
        setContent {
            EventBusTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumn(content = {
                        item {
                            Button(onClick = { /*TODO*/
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        TestClassActivity::class.java
                                    )
                                )
                            }) {
                                Text(text = "open TestActivity")
                            }
                        }
                        item {
                            Button(onClick = { /*TODO*/
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        TestLifecycleActivity::class.java
                                    )
                                )
                            }) {
                                Text(text = "open TestLifecycleActivity")
                            }
                        }
                        item {
                            Button(onClick = { /*TODO*/
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        TestViewModelActivity::class.java
                                    )
                                )
                            }) {
                                Text(text = "open TestViewModelActivity")
                            }
                        }

                        item {
                            Button(onClick = { /*TODO*/
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        TestFragmentActivity::class.java
                                    )
                                )
                            }) {
                                Text(text = "open TestFragmentActivity")
                            }
                        }
                    })


                }


            }

        }
    }


}

