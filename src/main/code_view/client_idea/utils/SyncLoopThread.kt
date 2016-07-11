package code_view.client_idea.utils

import com.intellij.openapi.application.ApplicationManager

class SyncLoopThread(run: SyncLoopThread.() -> Unit) {
    val delay = 100L
    var shouldStop = false


    val thread = ApplicationManager.getApplication().executeOnPooledThread {
        while (!shouldStop) {
            run()
            Thread.sleep(delay)
        }
    }

    fun onDispatchThread(run: SyncLoopThread.() -> Unit) {
        synchronized(this) {
            ApplicationManager.getApplication().invokeLater {
                run()
            }
        }
    }

    fun stop() {
        shouldStop = true
    }
}