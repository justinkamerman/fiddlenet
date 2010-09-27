/**
 * $Id$ 
 *
 * $LastChangedDate$ 
 * 
 * $LastChangedBy$
 */
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class Main
{
    public static void main (String[] args)
    {
        try
        {
            Options opt = new Options(); 
            opt.addOption("h", false, "Print help");
            opt.addOption("g", true, "Option g");
            opt.addOption("e", true, "Option e");
            BasicParser parser = new BasicParser();
            CommandLine cl = parser.parse(opt, args); 
            
            if ( cl.hasOption('h') ) 
            {
                HelpFormatter hf = new HelpFormatter();
                hf.printHelp("OptionsTip", opt);
                System.exit (0);
            }

            if (cl.hasOption ('g')) System.out.println("Option g => "  + cl.getOptionValue ('g'));
            if (cl.hasOption ('e')) System.out.println("Option e => "  + cl.getOptionValue ('e'));
        }
        catch (ParseException ex)
        {
            System.err.println (ex);
            System.exit (1);
        }
    }
}


