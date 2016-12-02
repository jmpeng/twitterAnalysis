package SearchInfo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.junit.Test;

public class TestSearchInfo extends TestCase {

	@Test
	public void testGood() {
				
		// Prepare to capture output
	    PrintStream originalOut = System.out;        
	    OutputStream os = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
	    
	    // Perform tests
	    SearchInfo.SearchServlet("good", "twitter_data.txt");
	    String str = os.toString();
	    assertEquals("11", str.substring(str.length() - 3, str.length() - 1));

	    // Restore normal operation
	    System.setOut(originalOut);

	}
	
	@Test
	public void testnotFound() {
				
		// Prepare to capture output
	    PrintStream originalOut = System.out;        
	    OutputStream os = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
	    
	    // Perform tests
	    SearchInfo.SearchServlet("pjm", "twitter_data.txt");
	    String str = os.toString();
	    assertEquals("0", str.substring(str.length() - 2, str.length() - 1));

	    // Restore normal operation
	    System.setOut(originalOut);

	}
	
	@Test
	public void testnoFile() {
				
		// Prepare to capture output
	    PrintStream originalOut = System.out;        
	    OutputStream os = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
	    
	    // Perform tests
	    SearchInfo.SearchServlet("good", "twitter_data1.txt");
	    String str = os.toString();
	    assertEquals(true, str.contains("No such file or directory)"));

	    // Restore normal operation
	    System.setOut(originalOut);

	}
	
	@Test
	public void testsmallFound() {
				
		// Prepare to capture output
	    PrintStream originalOut = System.out;        
	    OutputStream os = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
	    
	    // Perform tests
	    SearchInfo.SearchServlet("Monday", "twitter_data.txt");
	    String str = os.toString();
	    assertEquals("1", str.substring(str.length() - 2, str.length() - 1));

	    // Restore normal operation
	    System.setOut(originalOut);
	    	   
	}
	
	@Test
	public void testmanyFound() {
				
		// Prepare to capture output
	    PrintStream originalOut = System.out;        
	    OutputStream os = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
	    
	    // Perform tests
	    SearchInfo.SearchServlet("Trump", "twitter_data.txt");
	    String str = os.toString();
	    assertEquals("30", str.substring(str.length() - 3, str.length() - 1));

	    // Restore normal operation
	    System.setOut(originalOut);
	    	   
	}
	
	@Test
	public void testimageFile() {
				
		// Prepare to capture output
	    PrintStream originalOut = System.out;        
	    OutputStream os = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
	    
	    // Perform tests
	    SearchInfo.SearchServlet("good", "Chess.jpg");
	    String str = os.toString();
	    assertEquals("0", str.substring(str.length() - 2, str.length() - 1));

	    // Restore normal operation
	    System.setOut(originalOut);
	    	   
	}


}
