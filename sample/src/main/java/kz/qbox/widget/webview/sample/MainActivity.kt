package kz.qbox.widget.webview.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.qbox.widget.webview.core.Widget
import kz.qbox.widget.webview.core.models.Call
import kz.qbox.widget.webview.core.models.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Widget.isLoggingEnabled = true

        Widget.Builder(this)
            .setUrl(BuildConfig.WIDGET_URL)
            .setLanguage("ru")
            .setCall(
                Call(
                    topic = "test"
                )
            )
            .setUser(
                User(
                    firstName = "First name",
                    lastName = "Last name",
                    iin = "901020304050",
                    phoneNumber = "77771234567"
                )
            )
            .launch()
    }

}