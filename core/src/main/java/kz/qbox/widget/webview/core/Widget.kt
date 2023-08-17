package kz.qbox.widget.webview.core

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kz.qbox.widget.webview.core.models.*
import kz.qbox.widget.webview.core.ui.presentation.WebViewActivity

object Widget {

    var isLoggingEnabled: Boolean = false
        @Synchronized get
        @Synchronized set

    abstract class Builder internal constructor(private val context: Context) {

        class FullSuite constructor(context: Context) : Builder(context)

        class VideoCall constructor(context: Context) : Builder(context)

        private var isLoggingEnabled: Boolean? = null
        private var url: String? = null
        private var language: Language? = null
        private var call: Call? = null
        private var user: User? = null
        private var dynamicAttrs: DynamicAttrs? = null
        private var customActivity: AppCompatActivity? = null

        fun getLoggingEnabled(): Boolean = isLoggingEnabled ?: false

        fun setLoggingEnabled(isLoggingEnabled: Boolean): Builder {
            this.isLoggingEnabled = isLoggingEnabled
            return this
        }

        fun getUrl(): String? = url

        fun setUrl(url: String): Builder {
            this.url = url
            return this
        }

        fun getLanguage(): Language? = language

        fun setLanguage(language: Language): Builder {
            this.language = language
            return this
        }

        fun getCall(): Call? = call

        fun setCall(call: Call): Builder {
            this.call = call
            return this
        }

        fun getDynamicAttrs(): DynamicAttrs? = dynamicAttrs

        fun setDynamicAttrs(dynamicAttrs: DynamicAttrs): Builder {
            this.dynamicAttrs = dynamicAttrs
            return this
        }

        fun getUser(): User? = user

        fun setUser(user: User): Builder {
            this.user = user
            return this
        }

        fun getCustomActivity(): AppCompatActivity? = customActivity

        fun setCustomActivity(customActivity: AppCompatActivity): Builder {
            this.customActivity = customActivity
            return this
        }

        fun build(): Intent {
            isLoggingEnabled?.let {
                Widget.isLoggingEnabled = it
            }

            return if (customActivity == null) WebViewActivity.newIntent(
                context = context,
                flavor = when (this) {
                    is FullSuite -> Flavor.FULL_SUITE
                    is VideoCall -> Flavor.VIDEO_CALL
                    else -> throw IllegalStateException()
                },
                url = requireNotNull(url) { "Declare url, without it widget won't work!" },
                language = (language ?: Language.KAZAKH).code,
                call = call,
                user = user,
                dynamicAttrs = dynamicAttrs
            ) else {
                Intent(context, customActivity!!::class.java)
                    .putExtra(
                        "flavor",
                        when (this) {
                            is FullSuite -> Flavor.FULL_SUITE
                            is VideoCall -> Flavor.VIDEO_CALL
                            else -> throw IllegalStateException()
                        }
                    )
                    .putExtra("url", url)
                    .putExtra("language", language)
                    .putExtra("call", call)
                    .putExtra("user", user)
                    .putExtra("dynamic_attrs", dynamicAttrs)
            }
        }

        fun launch(): Intent {
            val intent = build()
            context.startActivity(intent)
            return intent
        }
    }

}