package code_view.client_idea.settings

import code_view.client_idea.utils.context
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import javax.swing.JPanel

class Dialog(val project: Project) : SearchableConfigurable {
    var ui: DialogUI? = null

    override fun getDisplayName() = "Code View"

    override fun getHelpTopic() = "preferences.Dialog"

    override fun getId() = "preferences.Dialog"

    override fun apply() {
        var url = ui!!.urlField.text
        if (url.last().equals("/")) {
            url = url.dropLast(1)
        }
        context(project) {
            it.settings.url = url
        }
    }

    override fun createComponent(): JPanel? {
        ui = DialogUI()
        return ui?.rootPanel
    }

    override fun reset() {
        context(project) {
            ui!!.urlField.text = it.settings.url
        }
    }

    override fun enableSearch(p0: String?) = null

    override fun disposeUIResources() {
        ui = null
    }

    override fun isModified() = context(project) {
        it.settings.url != ui!!.urlField.text
    }
}