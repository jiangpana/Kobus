package com.jansir.eventbus.lifecycle

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment

class ActivityWatcher : ObjectWatcher<Activity> {

    private val KEY = "com.jansir.eventbus.lifecycle.ActivityWatcher.OBJECT_KEY"

    override fun watch(obj: Activity, cb: ObjectWatcher.ObjectState) {
        var fragment = obj.fragmentManager.findFragmentByTag(KEY)
        if (fragment == null) {
            fragment = LifecycleFragment(cb)
            obj.fragmentManager.beginTransaction().add(fragment, KEY).commitAllowingStateLoss()
        }
    }

    @SuppressLint("ValidFragment")
    class LifecycleFragment(private val cb: ObjectWatcher.ObjectState) : Fragment() {
        private val TAG = javaClass.simpleName
        override fun onDestroy() {
            super.onDestroy()
            cb.onDestroy()
        }
    }
}