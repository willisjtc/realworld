package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext


class TagsController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.get("/tags").handler(get())
    }

    fun get() = Handler<RoutingContext> {

    }
}