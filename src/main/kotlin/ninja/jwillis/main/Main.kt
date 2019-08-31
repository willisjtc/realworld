package ninja.jwillis.main

import io.reactivex.plugins.RxJavaPlugins
import io.vertx.kotlin.core.deploymentOptionsOf
import io.vertx.reactivex.core.RxHelper
import io.vertx.reactivex.core.Vertx


fun main(args: Array<String>) {

    val vertx = Vertx.vertx()
    RxJavaPlugins.setComputationSchedulerHandler { s -> RxHelper.scheduler(vertx) }
    RxJavaPlugins.setIoSchedulerHandler { s -> RxHelper.blockingScheduler(vertx) }
    RxJavaPlugins.setNewThreadSchedulerHandler { s -> RxHelper.scheduler(vertx) }

    vertx.deployVerticle("ninja.jwillis.verticles.HttpVerticle", deploymentOptionsOf(instances = 3))
    vertx.deployVerticle("ninja.jwillis.verticles.DatabaseVerticle", deploymentOptionsOf(instances = 3, worker = true))
}

