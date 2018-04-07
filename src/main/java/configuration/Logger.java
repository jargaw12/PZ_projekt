package configuration;

import java.io.IOException;
import java.util.logging.FileHandler;

public class Logger {
    private static Logger ourInstance = new Logger();
    public java.util.logging.Logger logger;

    public static Logger getInstance() {
        if (ourInstance == null)
            ourInstance = new Logger();
        return ourInstance;
    }

    private Logger() {
        MyConfiguration configuration=new MyConfiguration();
        logger = java.util.logging.Logger.getLogger("LoggingSample2");
        FileHandler handler = null;
        try {
            handler = new FileHandler(configuration.getValue("dir.logger"), 10000, 5, true);
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
