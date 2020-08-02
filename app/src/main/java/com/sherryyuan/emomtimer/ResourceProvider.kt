package com.sherryyuan.emomtimer

import android.content.Context
import androidx.annotation.StringRes

/**
 * Class allowing non-Android classes to access resources.
 */
class ResourcesProvider(private val context: Context) {

    fun getString(@StringRes stringId: Int, vararg formatArgs: Any) =
        context.getString(stringId, *formatArgs)
}