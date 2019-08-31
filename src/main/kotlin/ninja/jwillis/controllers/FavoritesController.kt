package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext


class FavoritesController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.post("/articles/:slug/favorite").handler(post())
        apiRouter.delete("/articles/:slug/favorite").handler(delete())
    }

    fun post() = Handler<RoutingContext> {

    }

    fun delete() = Handler<RoutingContext> {

    }
}