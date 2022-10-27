package kz.qbox.widget.webview.core.ui.components

import android.webkit.JavascriptInterface
import kz.qbox.widget.webview.core.models.Call
import kz.qbox.widget.webview.core.models.User

internal class JSBridge constructor(
    private val call: Call,
    private val user: User,
    private var listener: Listener? = null
) {

    @JavascriptInterface
    fun getCall(): String? =
        call.toJSONObject().toString(4)

    @JavascriptInterface
    fun getUser(): String? =
        user.toJSONObject().toString(4)

    @JavascriptInterface
    fun onClose(): Boolean =
        listener?.close() == true

    fun dispose() {
        listener = null
    }

    interface Listener {
        fun close(): Boolean
    }

}