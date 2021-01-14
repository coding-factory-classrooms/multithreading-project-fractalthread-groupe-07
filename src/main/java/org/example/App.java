package org.example;

import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;

public class App {
    public static void main(String[] args) {
        initialize();


        FractalController fractalController = new FractalController();

        Spark.get("/", (req, res) -> fractalController.fractalControllerInitialise());
        Spark.get("/getImage", (req, res) -> fractalController.fractalRefresh(req.queryParamOrDefault("direction",null),req.queryParamOrDefault("type",null)));
        Spark.get("/setSides", (req, res) -> fractalController.fractalSizing(req.queryParamOrDefault("verticalSide", null), req.queryParamOrDefault("horizontalSide", null)));
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
