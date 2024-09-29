package br.com.michel.soundsynthesizer.domain

interface ResourcesProvider {
    fun getString(id: Int): String
    fun getString(id: Int, vararg params: Any?): String
}
