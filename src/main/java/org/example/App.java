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

        MandelController mandelController = new MandelController();

        Spark.get("/", (req, res) -> mandelController.MandelInitialise());
        Spark.post("/getImage/:direction", (req, res) -> mandelController.mandelRefresh(req.params().get("direction")));
//        Spark.get("/getImage", (req, res) -> mandelController.convertFileContentToBlob());
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
