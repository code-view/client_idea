package code_view.client_idea

import junit.framework.TestCase

class SessionTest : TestCase() {
//    fun testUpdate() {
//        val session = Session("test", null, null, null, null, null, null)
//        val newSession = session.update(
//                "main.py", "print 123", 10, 12, 14, 18)
//
//        assertEquals(
//                Session("test", "main.py", "print 123", 10, 12, 14, 18),
//                newSession)
//    }

    fun testJson() {
        val session = Session("test", "main.py", "print 123", 10, 12, 14, 18)
        assertEquals(
                """{"id":"test","fileName":"main.py","text":"print 123","selectionStartLine":10,"selectionStartColumn":12,"selectionEndLine":14,"selectionEndColumn":18}""",
                session.json)
    }

    fun testSerializeDeserialize() {
        val session = Session("test", "main.py", "print 123", 10, 12, 14, 18)
        val json = session.json
        val deserialized = Session.deserializer.deserialize(json)
        assertEquals(session, deserialized)
    }
}