package code_view.client_idea.utils

import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.*


class EditorListener(val ctx: Context) : CaretListener, DocumentListener, SelectionListener, VisibleAreaListener {
    val editorManager: EditorEventMulticaster = EditorFactory.getInstance().eventMulticaster

    fun listen() {
        editorManager.addDocumentListener(this)
        editorManager.addSelectionListener(this)
        editorManager.addCaretListener(this)
        editorManager.addVisibleAreaListener(this)
    }

    fun stop() {
        editorManager.removeSelectionListener(this)
        editorManager.removeDocumentListener(this)
        editorManager.removeCaretListener(this)
        editorManager.removeVisibleAreaListener(this)
    }

    override fun documentChanged(event: DocumentEvent) {
        ctx.sync.sync()
    }

    override fun caretAdded(caretEvent: CaretEvent) {
        ctx.sync.sync()
    }

    override fun caretRemoved(caretEvent: CaretEvent) {
        ctx.sync.sync()
    }

    override fun beforeDocumentChange(event: DocumentEvent) {
        ctx.sync.sync()
    }

    override fun caretPositionChanged(event: CaretEvent) {
        ctx.sync.sync()
    }

    override fun visibleAreaChanged(event: VisibleAreaEvent) {
        ctx.sync.sync()
    }

    override fun selectionChanged(event: SelectionEvent) {
        ctx.sync.sync()
    }
}
