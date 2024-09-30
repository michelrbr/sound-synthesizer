package br.com.michel.soundsynthesizer.domain

val testResourcesProvider = object : ResourcesProvider {
    override fun getString(id: Int): String = id.toString()

    override fun getString(id: Int, vararg params: Any?): String {
        return params.fold(
            id.toString()
        ) { acc: String, any: Any? ->
            acc + ":" + any.toString()
        }
    }
}
