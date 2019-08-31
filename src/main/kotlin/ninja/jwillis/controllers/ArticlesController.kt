package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext

class ArticlesController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.get("/articles/:slug").handler(get())
        apiRouter.post("/articles").handler(post())
        apiRouter.put("/articles/:slug").handler(put())
        apiRouter.delete("/articles/:slug").handler(delete())
    }

    fun get() = Handler<RoutingContext> {

    }

    fun post() = Handler<RoutingContext> {

    }

    fun put() = Handler<RoutingContext> {

    }

    fun delete() = Handler<RoutingContext> {

    }
}