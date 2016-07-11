package code_view.client_idea

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

data class Session(val id: String, val fileName: String?, val text: String?,
                   val selectionStartLine: Int?, val selectionStartColumn: Int?,
                   val selectionEndLine: Int?, val selectionEndColumn: Int?) {
    fun update(fileName: String, text: String,
               selectionStartLine: Int, selectionStartColumn: Int,
               selectionEndLine: Int?, selectionEndColumn: Int?)
            = Session(
            id, fileName, text, selectionStartLine, selectionStartColumn,
            selectionEndLine, selectionEndColumn)

    val json: String get() = gson.toJson(this)

    companion object {
        val gson = GsonBuilder().registerTypeAdapter<Session> {
            deserialize {
                Session(it.json["id"].string,
                        it.json["fileName"].nullString,
                        it.json["text"].nullString,
                        it.json["selectionStartLine"].nullInt,
                        it.json["selectionStartColumn"].nullInt,
                        it.json["selectionEndLine"].nullInt,
                        it.json["selectionEndColumn"].nullInt)
            }

            serialize {
                val serialized = JsonObject()
                serialized.putAll(
                        "id" to it.src.id,
                        "fileName" to it.src.fileName,
                        "text" to it.src.text,
                        "selectionStartLine" to it.src.selectionStartLine,
                        "selectionStartColumn" to it.src.selectionStartColumn,
                        "selectionEndLine" to it.src.selectionEndLine,
                        "selectionEndColumn" to it.src.selectionEndColumn)
                serialized
            }
        }.create()
    }

    object deserializer : ResponseDeserializable<Session> {
        override fun deserialize(content: String)
                = gson.fromJson<Session>(content)
    }
}