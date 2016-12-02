package SearchInfo;

import java.io.*;

public class SearchInfo {

	public static void SearchServlet(String keyword, String filename) {
	//	StringBuilder strbf = new StringBuilder();  // StringBuilder is faster than StringBuffer
		StringBuffer strbfTmp = new StringBuffer(); 
		boolean isFound = false;
		int count = 0;

		System.out.println("Output file under working Directory is \n" +
				System.getProperty("user.dir") +"/output.txt\n" );
		
		/* pjm test
		String curDir = System.getProperty("user.dir");
				File folder = new File(curDir);
				File[] listOfFiles = folder.listFiles();

				    for (int i = 0; i < listOfFiles.length; i++) {
				      if (listOfFiles[i].isFile()) {
				        System.out.println("File " + listOfFiles[i].getName());
				      } else if (listOfFiles[i].isDirectory()) {
				        System.out.println("Directory " + listOfFiles[i].getName());
				      }
				    }
				     pjm test*/
				    
		try 
		{
			// Create buffer to read file
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			
			// Create buffer to write file
			File fileOutput = new File("output.txt");
			FileWriter fw = new FileWriter(fileOutput.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			String line;
			//int linecount = 0;

			System.out.println("Searching \"" + keyword + "\" in file " + filename);

			while (( line = bf.readLine()) != null)
			{
				//linecount++;

				// Matches pattern "^@XXX$" to find the beginning of a new record
				if (line.matches("^@(\\S+)$") ||
					line.matches("^@(\\S+)\\s+\\S+$") ||
					line.matches("^@(\\S+)\\s+\\S+\\s+\\S+$") ||
					line.matches("^@(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+$") ||
					line.matches("^@(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+$")) {
					// The beginning of a new record
					if(isFound) {
						// Append the matched record to output buffer
						//strbf.append(strbfTmp + "\n\n");
						// Append the matched record to output file
						bw.append(strbfTmp + "\n\n");
						count++;
						isFound = false;
					}
					// Initialize strbfTmp with the new record
					strbfTmp.replace(0, strbfTmp.length(), line + "\n");				
				} else {
					strbfTmp.append(line + "\n");

					if(!isFound && line.contains(keyword))
					{
						//System.out.println("Word is matched on line " + linecount);
						isFound = true;
					}
				}
			}
			
			// Handle the last record
			if(isFound) {
				// Append the matched record to output buffer
				//strbf.append(strbfTmp + "\n\n");
				bw.append(strbfTmp + "\n\n");
				count++;
				isFound = false;
			}
			
			bf.close();// be sure to close BufferedReader
			bw.close();// be sure to close BufferedWriter
		}
		catch (IOException e) 
		{
			System.out.println("IO Error Occurred: " + e.toString());
		}
				
		System.out.println("\n" + count);

	}
	
	public static void main(String[] args) {
		// Parse the parameter which is the input filename
		if(args.length != 2) {
			System.err.println("Input parameters are incorrect.\nUsage:\nSearchInfo <Key word> <filename>");
			return;
		}		
		    
		String keyword = args[0];
		String filename = args[1];
		keyword = "good";
		filename = "Chess.jpg";
		SearchInfo.SearchServlet(keyword, filename);
		
	}

}
