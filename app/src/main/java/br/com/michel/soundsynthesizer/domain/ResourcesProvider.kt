package br.com.michel.soundsynthesizer.domain

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface ResourcesProvider {
    fun getString(id: Int): String
    fun getString(id: Int, vararg params: Any?): String
}

@Singleton
class ResourceProviderImpl @Inject constructor(
    @ApplicationContext val appContext: Context
) : ResourcesProvider {

    override fun getString(id: Int, vararg params: Any?): String = appContext.getString(id, *params)

    override fun getString(id: Int): String = appContext.getString(id)
}
