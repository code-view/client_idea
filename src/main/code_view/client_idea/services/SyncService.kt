package code_view.client_idea.services

import code_view.client_idea.Client
import code_view.client_idea.Session
import code_view.client_idea.settings.url
import code_view.client_idea.utils.context
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiUtilBase
import java.util.concurrent.LinkedBlockingQueue


class SyncService {
    lateinit var project: Project
    lateinit var client: Client
    lateinit var session: Session
    val queue = LinkedBlockingQueue<Session>()
    var syncing = false

    init {
        ApplicationManager.getApplication().executeOnPooledThread {
            var previousSession: Session? = null
            while (true) {
                val session = queue.take()
                if (session != previousSession && session.fileName != null) {
                    client.updateSession(session)
                    previousSession = session
                }
            }
        }
    }

    fun updateSession(session: Session): Session {
        val editor = FileEditorManager.getInstance(project).selectedTextEditor
        val caret = editor?.caretModel?.currentCaret
        if (editor is Editor && caret is Caret) {
            val file = PsiUtilBase.getPsiFileInEditor(editor, project)
            if (file is PsiFile) {
                return session.update(file, caret)
            }
        }

        return session
    }

    fun stop() {
        syncing = false
    }

    fun start(): Boolean {
        val (newSession, error) = client.createSession()

        if (newSession is Session) {
            syncing = true
            session = newSession
            return true
        } else {
            error?.printStackTrace()
            return false
        }
    }

    fun update(project: Project): SyncService {
        this.project = project
        context(project) {
            client = Client(it.settings.url)
        }
        return this
    }

    fun sync() {
        session = updateSession(session)
        if (!queue.isEmpty()) {
            queue.clear()
        }

        queue.offer(session)
    }

    companion object {
        fun getInstance(project: Project) = ServiceManager
                .getService(project, SyncService::class.java)
                .update(project)
    }
}