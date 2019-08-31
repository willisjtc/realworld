package ninja.jwillis.config

import io.reactivex.Single
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.config.configRetrieverOptionsOf
import io.vertx.kotlin.config.configStoreOptionsOf
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.reactivex.config.ConfigRetriever
import io.vertx.reactivex.core.Vertx


fun main() {
    val vertx = Vertx.vertx()
    Config.config(vertx).doOnSuccess { println(it) }.subscribe()
}

object Config {
    fun config(vertx: Vertx): Single<JsonObject> {
        val fileStore = configStoreOptionsOf(type = "file",
                                             config = jsonObjectOf("path" to "config.json"))
        val jsonStore = configStoreOptionsOf(type = "json")

        val retriever = ConfigRetriever.create(vertx, configRetrieverOptionsOf(stores = listOf(fileStore, jsonStore)))

        return retriever.rxGetConfig()
    }
}
