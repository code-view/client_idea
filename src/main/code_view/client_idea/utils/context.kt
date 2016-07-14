package code_view.client_idea.utils

import code_view.client_idea.services.SyncService
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project

class Context(val project: Project) {
    val sync by lazy { SyncService.getInstance(project) }
    val settings by lazy { PropertiesComponent.getInstance(project)!! }
    val listener by lazy { EditorListener(this) }
}

fun <T> context(project: Project, run: (context: Context) -> T) =
        run(Context(project))

fun <T> context(e: AnActionEvent, run: (context: Context) -> T) =
        run(Context(e.getData(PlatformDataKeys.PROJECT)!!))
