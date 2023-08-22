package androidx.lifecycle

import com.jansir.eventbus.lifecycle.ObjectWatcher
import java.io.Closeable

class ViewModelWatcher : ObjectWatcher<ViewModel> {

    private val KEY = "androidx.lifecycle.ViewModelWatcher.OBJECT_KEY"

    override fun watch(obj: ViewModel, cb: ObjectWatcher.ObjectState) {
        if (null != obj.getTag(KEY)) return
        obj.setTagIfAbsent(KEY, object : Closeable {
            override fun close() {
                cb.onDestroy()
            }
        })
    }
}