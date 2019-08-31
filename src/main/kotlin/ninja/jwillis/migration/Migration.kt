package ninja.jwillis.migration

import io.vertx.reactivex.core.Vertx
import ninja.jwillis.config.Config
import org.flywaydb.core.Flyway

fun main() {
    // Create the Flyway instance and point it to the database
    val vertx = Vertx.vertx()
    Config.config(vertx).map {
        val dbConfig = it.getJsonObject("database")
        val url = "jdbc:postgresql://${dbConfig.getString("host")}:${dbConfig.getInteger("port")}/${dbConfig.getString("name")}"
        val user = dbConfig.getString("user")
        val password = dbConfig.getString("password")
        listOf("public", "test").forEach {
            val flyway = Flyway.configure().placeholders(mutableMapOf("shard_num" to "shard_0", "schema" to it)).schemas(it).dataSource(url, user, password).load()
            flyway.migrate()
        }

    }.doAfterTerminate { vertx.rxClose().subscribe() }
    .subscribe({ println("Successfully completed migration") }, { println(it.printStackTrace()) })
}
