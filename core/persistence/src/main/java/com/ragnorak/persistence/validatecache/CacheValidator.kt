package com.ragnorak.persistence.validatecache

object CacheValidator {
    private var lastFetchTimeGlobal: Long? = null
    private const val CACHE_VALIDITY_MS = 60 * 1000L

    fun isCacheValid(lastFetchTime: Long? = lastFetchTimeGlobal): Boolean {
        val currentTime = System.currentTimeMillis()
        return lastFetchTime?.let { currentTime - it < CACHE_VALIDITY_MS } ?: false
    }

    fun updateFetchTime() {
        lastFetchTimeGlobal = System.currentTimeMillis()
    }
}