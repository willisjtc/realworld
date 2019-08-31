package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext


class UsersController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.get("/user").handler(get())
        apiRouter.post("/users").handler(post())
        apiRouter.put("/user").handler(put())
    }

    fun get() = Handler<RoutingContext> {

    }

    fun post() = Handler<RoutingContext> {

    }

    fun put() = Handler<RoutingContext> {

    }
}