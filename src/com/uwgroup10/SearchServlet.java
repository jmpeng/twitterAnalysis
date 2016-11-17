/* Author: Jimei Peng
 * Date:  November 7, 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.cloudoe.samples;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SearchServlet extends HttpServlet{
	public void service(HttpServletRequest req,
			HttpServletResponse res)
					throws IOException
	{
		String keywords = req.getParameter("keywords");

		res.setContentType("text/plain");


		String filename = "/text/twitter_data.txt";

		ServletContext context = getServletContext();

		StringBuffer strbfTmp = new StringBuffer(); 
		boolean isFound = false;

		//
		// First get the file InputStream using ServletContext.getResourceAsStream()
		// method.
		//
		InputStream is = context.getResourceAsStream(filename);
		if (is != null) {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			PrintWriter writer = res.getWriter();

			String text;
			// Count how many twitters have been matched with the keywords
			int count = 0;

			while ((text = reader.readLine()) != null) {											

				/* Matches pattern "^@XXX$", "^@XXX XXX$", "^@XXX XXX XXX$",
				 * "^@XXX XXX XXX XXX$" or "^@XXX XXX XXX XXX XXX$" to find 
				 * the beginning of a new twitter
				 * For examples: 
				 * @t a y ( :
				 * @Spurs 4-1
				 * @Enid Nacon
				 * @Max
				 * @The B.S. Observer
				 */
				if (text.matches("^@(\\S+)$") ||
						text.matches("^@(\\S+)\\s+\\S+$") ||
						text.matches("^@(\\S+)\\s+\\S+\\s+\\S+$") ||
						text.matches("^@(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+$") ||
						text.matches("^@(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+$")) {
					// The beginning of a new twitter
					if(isFound) {
						// Append the matched twitter to output buffer
						writer.println(strbfTmp);
						count++;

						isFound = false;
					}
					// Initialize strbfTmp with the new twitter
					strbfTmp.replace(0, strbfTmp.length(), text + "\n");				
				} else {
					strbfTmp.append(text + "\n");

					if(!isFound && text.contains(keywords))
					{
						isFound = true;
					}
				}
			}

			// Handle the last twitter
			if(isFound) {
				// Append the matched twitter to output buffer
				writer.println(strbfTmp);
				count++;
				isFound = false;
			}	

			writer.println("\nTotal " + count + " twitters are matched!\n\n");

		}
	}
}
