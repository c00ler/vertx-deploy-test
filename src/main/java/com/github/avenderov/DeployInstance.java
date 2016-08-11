package com.github.avenderov;

import com.github.avenderov.verticles.HttpServerVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author  Alexey Venderov
 */
public class DeployInstance {

    public static void main(final String[] args) {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");

        final Logger logger = LoggerFactory.getLogger(DeployName.class);

        final int port = 9000;

        final Vertx vertx = Vertx.vertx(new VertxOptions().setEventLoopPoolSize(4));
        final DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("port", port));

        for (int i = 0; i < 4; i++) {
            vertx.deployVerticle(new HttpServerVerticle(), options,
                asyncResult -> {
                    if (!asyncResult.succeeded()) {
                        logger.error("Unable to start the server", asyncResult.cause());
                        System.exit(1);
                    }
                });
        }

        logger.info("Listening on port " + port + "...");
    }

}
