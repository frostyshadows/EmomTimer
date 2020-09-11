package com.sherryyuan.emomtimer.workout.addoreditworkout

import android.content.Context
import android.widget.ArrayAdapter
import kotlin.math.min

private const val ADAPTER_LIMIT = 3

/**
 * Custom ArrayAdapter that limits number of results to 3 objects.
 */
class ExerciseAutocompleteArrayAdapter<T>(
    context: Context, textViewResourceId: Int,
    objects: List<T>
) : ArrayAdapter<T>(context, textViewResourceId, objects) {

    override fun getCount(): Int = min(ADAPTER_LIMIT, super.getCount())
}
