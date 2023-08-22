package com.jansir.sample

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
import com.jansir.eventbus.core.observeEvent
import com.jansir.sample.ui.theme.EventBusTheme
import kotlinx.coroutines.*

class TestFragmentActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    val TAG = javaClass.simpleName

    var fragment: Fragment? = TestFragment()
    var xfragment: androidx.fragment.app.Fragment? = TestAndroidXFragment()


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

                            Button(onClick = {
                                fragment = TestFragment()
                                val transaction = fragmentManager.beginTransaction()
                                transaction.add(fragment, "")
                                transaction.commit()

                                launch {
                                    delay(6000)
                                    val ft = fragmentManager.beginTransaction()
                                    ft.remove(fragment)
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                    ft.commit()
                                    fragment = null
                                }

                            }) {
                                Text(text = "test fragment")
                            }

                            Button(onClick = {
                                xfragment = TestAndroidXFragment()
                                xfragment?.apply {
                                    supportFragmentManager.beginTransaction()
                                        .add(this, "")
                                        .show(this).commit()
                                }


                                launch {
                                    delay(6000)
                                    xfragment?.apply {
                                        supportFragmentManager.beginTransaction()
                                            .hide(this)
                                            .remove(this)
                                            .setTransition(TRANSIT_FRAGMENT_CLOSE)
                                            .commit()
                                    }
                                    xfragment = null
                                }

                            }) {
                                Text(text = "test x fragment")
                            }


                        }


                    })

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }


}

