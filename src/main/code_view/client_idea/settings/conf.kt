package code_view.client_idea.settings

import com.intellij.ide.util.PropertiesComponent

object conf {
    val DEFAULT_URL = "https://code-view.io"
    val URL_SETTING = "code_view.url"
}

var PropertiesComponent.url: String
    get() = getValue(conf.URL_SETTING, conf.DEFAULT_URL)
    set(value) = setValue(conf.URL_SETTING, value)
