package org.example;

import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;

import java.io.InputStreamReader;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        initialize();

        int basicSide = 1000;
        RefreshController refreshController = new RefreshController(basicSide);

        Spark.get("/", (req, res) -> refreshController.mandelInitialise());
        Spark.get("/getImage", (req, res) -> refreshController.fractalRefresh(req.queryParamOrDefault("direction",null),
                req.queryParamOrDefault("type","null")));
    }

    static void initialize() {
        Template.initialize();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);
        final LoggerMiddleware log = new LoggerMiddleware();
        Spark.before(log::process);
    }
}
