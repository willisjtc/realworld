package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext


class FeedsController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.get("/articles/feed").handler(get())
    }

    fun get() = Handler<RoutingContext> {

    }
}