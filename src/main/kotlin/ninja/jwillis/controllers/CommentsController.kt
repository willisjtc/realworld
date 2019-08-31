package ninja.jwillis.controllers

import io.vertx.core.Handler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext


class CommentsController(apiRouter: Router) : Controller(apiRouter) {

    init {
        apiRouter.get("/articles/:slug/comments").handler(get())
        apiRouter.post("/articles/:slug/comments").handler(post())
        apiRouter.delete("/articles/:slug/comments/:id").handler(delete())
    }

    fun get() = Handler<RoutingContext> {

    }

    fun post() = Handler<RoutingContext> {

    }

    fun delete() = Handler<RoutingContext> {

    }

}