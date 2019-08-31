package ninja.jwillis.verticles

import io.reactivex.Completable
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.Router
import ninja.jwillis.controllers.*

class HttpVerticle() : AbstractVerticle() {

    override fun rxStart(): Completable {
        println("deploying http verticle - vertx: $vertx")

        val baseRouter = Router.router(vertx)
        val apiRouter = Router.router(vertx);
        baseRouter.mountSubRouter("/api", apiRouter)

        ArticlesController(apiRouter)
        CommentsController(apiRouter)
        FavoritesController(apiRouter)
        FeedsController(apiRouter)
        FollowsController(apiRouter)
        LoginController(apiRouter)
        ProfilesController(apiRouter)
        UsersController(apiRouter)
        return vertx.createHttpServer()
                    .requestHandler(baseRouter::accept)
                    .rxListen(8081)
                    .ignoreElement()
    }
}