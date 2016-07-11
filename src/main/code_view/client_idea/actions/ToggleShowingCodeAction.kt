package code_view.client_idea.actions

import code_view.client_idea.Session
import code_view.client_idea.settings.url
import code_view.client_idea.utils.context
import com.intellij.icons.AllIcons
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.project.Project

class ToggleShowingCodeAction : AnAction() {
    val START_TEXT = "Start showing code"
    val STOP_TEXT = "Stop showing code"

    init {
        onNotShowing(templatePresentation)
    }

    fun onShowing(presentation: Presentation) {
        presentation.apply {
            text = STOP_TEXT
            icon = AllIcons.Process.Stop
            description = STOP_TEXT
        }
    }

    fun onNotShowing(presentation: Presentation) {
        presentation.apply {
            text = START_TEXT
            icon = AllIcons.General.Run
            description = START_TEXT
        }
    }

    fun showSyncingNotification(project: Project, session: Session) {
        context(project) {
            val url = "${it.settings.url}/session/${session.id}/"
            val message = "Session id: ${session.id}<br>" +
                    "<a href='$url'>" +
                    "Open in browser" +
                    "</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a href='#copy'>Copy link</a>"
            val notification = Notification(
                    "code_view.client_idea",
                    "Code View translation started",
                    message,
                    NotificationType.INFORMATION,
                    ActionNotificationListener(url))
            Notifications.Bus.notify(notification, project)
        }
    }

    fun showErrorNotification(project: Project) {
        val notification = Notification(
                "code_view.client_idea",
                "Code View can't start translation",
                "Can't start translation, please try" +
                        " again or change url in settings",
                NotificationType.INFORMATION)
        Notifications.Bus.notify(notification, project)
    }

    override fun actionPerformed(e: AnActionEvent) {
        context(e) {
            if (it.sync.syncing) {
                it.sync.stop()
            } else {
                if (it.sync.start()) {
                    showSyncingNotification(it.sync.project, it.sync.session)
                } else {
                    showErrorNotification(it.sync.project)
                }
            }
        }
    }

    override fun update(e: AnActionEvent) {
        context(e) {
            if (it.sync.syncing) {
                onShowing(e.presentation)
            } else {
                onNotShowing(e.presentation)
            }
        }
    }
}
