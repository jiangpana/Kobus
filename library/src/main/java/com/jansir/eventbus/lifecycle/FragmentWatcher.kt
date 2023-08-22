package com.jansir.eventbus.lifecycle

import android.annotation.SuppressLint
import android.app.Fragment

class FragmentWatcher : ObjectWatcher<Fragment> {

    private val KEY = "com.jansir.eventbus.lifecycle.FragmentWatcher.OBJECT_KEY"

    override fun watch(obj: Fragment, cb: ObjectWatcher.ObjectState) {
        var fragment = obj.childFragmentManager.findFragmentByTag(KEY)
        if (fragment == null) {
            fragment = LifecycleFragment(cb)
            obj.childFragmentManager.beginTransaction().add(fragment, KEY).commitAllowingStateLoss()
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