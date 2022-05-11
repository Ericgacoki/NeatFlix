package com.ericg.neatflix.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <T : R, R> Flow<T>.collectAsStateLifecycleAware(
    initial: R, context: CoroutineContext = EmptyCoroutineContext
): State<R> {
    val lifecycleAwareFlow = this
    return lifecycleAwareFlow.collectAsState(initial = initial, context = context)
}
