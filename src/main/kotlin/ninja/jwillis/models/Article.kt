package ninja.jwillis.models

import io.vertx.reactivex.pgclient.PgPool

class Article(pool: PgPool) :  Model(pool, "articles") {


}