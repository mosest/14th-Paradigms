import java.net.*;
import java.io.*;
import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;

class Main {
	// -------------------------------------------------------------------------------
	// constructPayload(String):
	// 
	// Constructs payload using the path to the parent file!
	// 1. If there's no file sent, then we have the two boxes of files and folders.
	// 2. If there is a file sent, then we just show a box with the file and a
	//	  button to go back to the file navigation two-box thing!
	// -------------------------------------------------------------------------------
	
	public static String constructPayload(String pathToParent, String nameOfFileToSend) {
		String total = "";
		System.out.println("Path = '" + pathToParent + "'");
		System.out.println("File = '" + nameOfFileToSend + "'");
		
		// If there's nothing to send, then just give two boxes!
		if (nameOfFileToSend.equals("")) { 
			// This is the HTML code before we add in the folders!
			String first = "<table><tr><td>"
					+ "<b>Folders:</b>"
					+ "<br><select id=\"folderList\" size=\"15\" style=\"width: 280px\" onchange=\"javascript:location.href=this.value;\">";
			
			// This is the (optional) HTML code for the '..' folder (to go up a level)
			
			// Get the path to the folder above parent if user wants it :3
			String newPath = pathToParent; 											// start with the regular path
			if (pathToParent.endsWith("/")) 										// if it ends with a slash, cut that off
				newPath = pathToParent.substring(0,pathToParent.length() - 1);
			
			int indexOfSlash = newPath.lastIndexOf("/");							// then find the slash before the last file
			newPath = newPath.substring(0,indexOfSlash);							// and cut that off :3
			
			// Set the value of the '..' folder to link to the newPath
			// (without the dot at the front) :D
			first += "<option value=\"" + newPath.substring(1,newPath.length()) + "\"><</option>";
			
			// This is the HTML code after the folders, but before the files :D
			String second = "</select></td><td>"
					+ "<b>Files:</b><br>"
					+ "<select id=\"fileList\" size=\"15\" style=\"width: 280px\" onchange=\"javascript:location.href=this.value;\">";
			
			// This is the HTML code to finish up all the HTML code :^)
			String third = "</select></td></tr></table>";
			
			// Add all the folders and files to first and second
			File parentFolder = new File(pathToParent);
			File[] listOfFiles = parentFolder.listFiles();
			
			if (listOfFiles != null) {
				for (File currentFile : listOfFiles) {
					String name = currentFile.getName();
					String pathNoFirstDot = pathToParent.substring(1,pathToParent.length());
					
					if (currentFile.isFile()) {					
						second += "<option value=\"" + pathNoFirstDot + "/" + name + "\">" + name + "</option>";
					} else if (currentFile.isDirectory()) {
						first += "<option value=\"" + pathNoFirstDot + "/" + name + "\">" + name + "</option>";
					}
				}
			}
			
			// Make payload (= payloadFirst + payloadSecond + payloadThird)
			total = first + second + third;
		}
		
		// If there's a file to send, then show it!
		else {
			System.out.println("Trying to read " + nameOfFileToSend + "!");
			// Send a file if that's what the user wants!
			total = "<b>" + nameOfFileToSend + ":</b>"
					+ "<br>"
					+ "<p id=\"fileSent\" size=\"15\" style=\"width: 280px\">";
			
			// Add all the lines of the file into the box!
			File fileRequested = new File(pathToParent);
			Scanner fileScan;
			try {
				fileScan = new Scanner(fileRequested);
				
				while (fileScan.hasNext()) {
					String nextLine = fileScan.nextLine();
					System.out.println(nextLine);
					total += nextLine + "<br>";
				}
				
				fileScan.close();
			} catch (FileNotFoundException e) {
				System.out.println("Don't know where '" + pathToParent + "' is!");
				total += "File not found :(<br>";
			}
			
			// Fix the path so next time it won't ask for a path where a file is treated as a folder lol
			int lastSlashIndex = pathToParent.lastIndexOf("/");
			pathToParent = pathToParent.substring(0,lastSlashIndex);
			
			// Close up the box!
			total += "</p>"
					+ "<br>"
					+ "<button value=" + pathToParent.substring(1) + " onclick=\"javascript:location.href=this.value;\">Back to file navigation!</button>";
		}
		
		// Return the full string all pieced together :3
		return total;
	}

	// -------------------------------------------------------------------------------
	// Main Function
	// -------------------------------------------------------------------------------
	
	public static void main(String[] args) throws Exception {
		// Set a path to start out with!
		String path = "./src";
		
		// Listen for a connection from a client
		ServerSocket serverSocket = new ServerSocket(1237);

		if(Desktop.isDesktopSupported())
			Desktop.getDesktop().browse(new URI("http://localhost:1237"));
		else
			System.out.println("Please direct your browser to http://localhost:1237.");
		
		while (true) {
			// Accept a connection!
			Socket clientSocket = serverSocket.accept();
			
			// Figure out what to send now that we have a connection with the client!
			System.out.println("Got a connection!");
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String nameOfFile = ""; // if the user clicked on a file, then this will be its filename :3
			
			// Get the URL request from the client!
			String URLRequest;
			if ((URLRequest = in.readLine()) != null) {
				System.out.println("The client said: " + URLRequest);
				// Get the URL requested (without GET and HTTP/1.1)
				String url = URLRequest.substring(4, URLRequest.length() - 9);
				
				if (!url.equals("/")) 
					path = "." + url;	// the '.' is added because it basically signifies the file where the project is
				
				// If the path has a dot in it (like '/src/sendme.txt'),
				// then the user wants us to send a file!
				if (url.contains(".")) {
					// Get the name of the file from the url requested
					int lastSlashIndex = url.lastIndexOf("/");
					nameOfFile = url.substring(lastSlashIndex + 1, url.length());
				}
			}
			
			// Construct payload! :D
			String payload = constructPayload(path,nameOfFile);
			
			// Send HTTP headers
			System.out.println("Sending a response...");
			out.print("HTTP/1.1 200 OK\r\n");
			out.print("Content-Type: text/html\r\n");
			out.print("Content-Length: " + Integer.toString(payload.length()) + "\r\n");
			out.print("Connection: close\r\n");
			out.print("\r\n");
	
			// Send the payload
			out.println(payload);
		}
	}
}