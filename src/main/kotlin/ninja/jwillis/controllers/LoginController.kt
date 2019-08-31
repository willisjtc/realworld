package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext


class LoginController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.post("/users/login").handler(post())
    }

    fun post() = Handler<RoutingContext> {

    }
}