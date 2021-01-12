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

        int verticalSide = 1000;
        int horizontalSide = 1000;
        MandelController mandelController = new MandelController(verticalSide, horizontalSide);

        Spark.get("/", (req, res) -> mandelController.mandelInitialise());
        Spark.get("/getImage", (req, res) -> mandelController.mandelRefresh(req.queryParamOrDefault("direction",null)));
        Spark.get("/setVerticalSide", (req, res) -> mandelController.mandelVerticalSizing(req.queryParamOrDefault("verticalSide", null)));
        Spark.get("/setHorizontalSide", (req, res) -> mandelController.mandelHorizontalSizing(req.queryParamOrDefault("horizontalSide", null)));

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
