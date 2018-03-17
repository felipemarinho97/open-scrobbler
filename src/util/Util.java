package util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Util {
	public static String stackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        
        return sw.toString();		
	}

	public static String loadLicense() {
		FileReader reader = null;
		try {
			reader = new FileReader("LICENSE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
	    String line;
		try {
			line = br.readLine();
			
		    while (line != null) {
		        sb.append(line.trim());
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return sb.toString();
	}

}
