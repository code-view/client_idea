package code_view.client_idea

import com.github.kittinunf.fuel.Fuel

class Client(val server: String) {
    fun createSession() = Fuel
            .post("$server/api/session/")
            .responseObject(Session.deserializer)
            .third


    fun updateSession(session: Session) = Fuel
            .put("$server/api/session/${session.id}/")
            .body(session.json)
            .response()
            .third
}
