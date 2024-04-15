package au.com.agl.kotlincats.presentation.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog
import au.com.agl.kotlincats.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Dialogs {
    fun displayError(context: Context, errorMessage: String?) {
        var alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setTitle(context.resources.getString(R.string.error_title))
        alertBuilder.setMessage(errorMessage ?: context.resources.getString(R.string.error_message))
        alertBuilder.setPositiveButton(context.resources.getString(R.string.ok_button), null)
        alertBuilder.show()
    }
}