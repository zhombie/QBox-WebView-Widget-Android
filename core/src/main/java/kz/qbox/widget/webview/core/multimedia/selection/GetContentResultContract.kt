package kz.qbox.widget.webview.core.multimedia.selection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

internal class GetContentResultContract :
    ActivityResultContract<GetContentResultContract.Params, Uri?>() {

    class Params constructor(
        val mimeTypes: Array<String>
    ) {
        constructor(mimeType: String) : this(arrayOf(mimeType))
    }

    override fun createIntent(context: Context, input: Params): Intent =
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, input.mimeTypes)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == Activity.RESULT_OK) intent?.data else null
    }

}
