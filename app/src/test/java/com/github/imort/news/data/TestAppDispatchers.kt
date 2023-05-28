package com.github.imort.news.data

import com.github.imort.news.AppDispatchers
import kotlinx.coroutines.Dispatchers

object TestAppDispatchers : AppDispatchers {
    override val default = Dispatchers.Unconfined
    override val main = Dispatchers.Unconfined
    override val io = Dispatchers.Unconfined
}