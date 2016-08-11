package com.github.avenderov.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author  Alexey Venderov
 */
public class HttpServerVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        logger.info("Starting...");

        final HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(request -> {
            logger.info("Got request " + request.path());

            request.response().end("Hello, World!");
        });

        httpServer.listen(config().getInteger("port"),
            asyncResult -> {
                if (asyncResult.succeeded()) {
                    startFuture.complete();
                } else {
                    startFuture.fail(asyncResult.cause());
                }
            });
    }

}
