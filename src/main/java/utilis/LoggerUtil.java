package utilis;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
//import org.apache.log4j.Logger;

public class LoggerUtil {
    // Declare the static logger variable
	org.apache.logging.log4j.core.LoggerContext context = org.apache.logging.log4j.core.config.Configurator.initialize(null, "\\src\\test\\resources\\log4j2.properties");

    public static final Logger logger = LogManager.getLogger();

}

