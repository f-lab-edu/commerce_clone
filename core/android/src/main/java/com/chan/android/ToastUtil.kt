package com.chan.android

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null

    fun show(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun cancel() {
        toast?.cancel()
        toast = null
    }
}