package code_view.client_idea.actions

import com.intellij.notification.Notification
import com.intellij.notification.NotificationListener
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.event.HyperlinkEvent

class ActionNotificationListener(val url: String) : NotificationListener.UrlOpeningListener(false) {
    fun copyUrl() {
        val data = StringSelection(url)
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(data, data)
    }

    override fun hyperlinkActivated(notification: Notification, event: HyperlinkEvent) {
        if (event.description == "#copy") {
            copyUrl()
        } else {
            super.hyperlinkActivated(notification, event)
        }
    }
}
