/**
 * @author Deependra.Rajawat
 *
 */
package library;

import org.apache.log4j.PropertyConfigurator;


public class Logger_Log4j {

	static String userDir = System.getProperty("user.dir");
	static String fileName =userDir+"\\log4j.properties";
	public static  final Logger_Log4j logger = Logger_Log4j.getLogger();
			
	public static Logger_Log4j getLogger(){
		PropertyConfigurator.configure(fileName);
		return logger;
	}

}
