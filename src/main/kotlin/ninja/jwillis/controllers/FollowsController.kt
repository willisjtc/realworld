package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext

class FollowsController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.get("/profiles/:username/follow").handler(post())
        apiRouter.post("/profiles/:username/follow").handler(delete())
    }

    fun post() = Handler<RoutingContext> {

    }

    fun delete() = Handler<RoutingContext> {

    }
}