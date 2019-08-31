package ninja.jwillis.verticles

import io.reactivex.Completable
import io.reactivex.Single
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.pgclient.pgConnectOptionsOf
import io.vertx.kotlin.sqlclient.poolOptionsOf
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.pgclient.PgPool
import ninja.jwillis.config.Config
import ninja.jwillis.models.*
import java.lang.reflect.Parameter


class DatabaseVerticle : AbstractVerticle() {

    lateinit var resources : Map<String, Any>

    override fun rxStart(): Completable {
        println("deploying database verticle - vertx: $vertx")

        return configureConnections(vertx).map { pgPool ->
                resources = mapOf("article" to Article(pgPool),
                                  "comment" to Comment(pgPool),
                                  "follow" to Follow(pgPool),
                                  "favorite" to Favorite(pgPool),
                                  "tag" to Tag(pgPool),
                                  "user" to User(pgPool))
                vertx.eventBus().consumer<JsonObject>("db.realworld").toObservable().map {
                    val headers = it.headers()
                    val resource = headers["resource"]
                    val action = headers["action"]
                    val body = it.body()
                    val resourceObj = resources[resource]
                    if (resourceObj != null) {
                        val method = resourceObj.javaClass.methods.filter { it.name.toLowerCase() == action.toLowerCase() }.first()
                        method.invoke(resourceObj, *params(body, method.parameters))
                    } else {
                        throw IllegalArgumentException("invalid action: $action, on resource: $resource")
                    }
            }
        }.ignoreElement()
    }

    private fun configureConnections(vertx: Vertx) : Single<PgPool> = Config.config(vertx).map {
        val connectOptions = pgConnectOptionsOf(port = it.getInteger("port"),
                                                host = it.getString("host"),
                                                database = it.getString("name"),
                                                user = it.getString("user"),
                                                password = it.getString("password"),
                                                properties = mapOf("search_path" to config().getString("schema", "public")))
        val poolOptions = poolOptionsOf(maxSize = 10)
        val pool = PgPool.pool(vertx, connectOptions, poolOptions)
        pool
    }

    private fun params(body: JsonObject, params: Array<Parameter>) =
        params.fold(listOf<Any>()) { acc, param ->
            acc + body.getValue(param.name)
        }.toTypedArray()
}