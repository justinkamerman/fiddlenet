/**
 * $Id: Instance.java 4 2010-09-27 20:14:47Z justinkamerman $ 
 *
 * $LastChangedDate: 2010-09-27 17:14:47 -0300 (Mon, 27 Sep 2010) $ 
 * 
 * $LastChangedBy: justinkamerman $
 */
package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class LogFormatter extends Formatter
{
    private static final DateFormat format = new SimpleDateFormat("h:mm:ss");
	private static final String lineSep = System.getProperty("line.separator");
    
	public String format (LogRecord record) 
    {
		String loggerName = record.getLoggerName();
		if (loggerName == null) 	loggerName = "root";

		StringBuilder output = new StringBuilder()
            .append("[")
            .append(format.format (new Date(record.getMillis())))
            .append("][")
            .append(record.getLevel())
            .append("][")
			.append(loggerName)
			.append("]: ")
			.append(record.getMessage()).append(' ')
			.append(lineSep);

		return output.toString();		
	}
}
    