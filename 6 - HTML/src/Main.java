import java.net.*;
import java.io.*;
import java.awt.Desktop;
import java.net.URI;

class Main {
	public static void main(String[] args) throws Exception {
		// Set a path to start out with!
		String path = "./src";
		
		// Listen for a connection from a client
		ServerSocket serverSocket = new ServerSocket(1234);

		if(Desktop.isDesktopSupported())
			Desktop.getDesktop().browse(new URI("http://localhost:1234"));
		else
			System.out.println("Please direct your browser to http://localhost:1234.");
		
		while (true) {
			// Accept a connection!
			Socket clientSocket = serverSocket.accept();
			
			// Figure out what to send now that we have a connection with the client!
			System.out.println("Got a connection!");
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
			// Get the URL request from the client!
			String URLRequest;
			if ((URLRequest = in.readLine()) != null) {
				System.out.println("The client said: " + URLRequest);
				path = "." + URLRequest.substring(4, URLRequest.length() - 9);
				System.out.println("The path requested is: '" + path + "'");
			}String payloadFirst = "<table><tr><td>"
					+ "<b>Folders:</b>"
					+ "<br><select id=\"folderList\" size=\"15\" style=\"width: 280px\" onchange=\"javascript:location.href=this.value;\">"
					+ "<option value=\"../\">..</option>";
			String payloadSecond = "</select></td><td>"
					+ "<b>Files:</b><br>"
					+ "<select id=\"fileList\" size=\"15\" style=\"width: 280px\">";
			String payloadThird = "</select></td></tr></table>";
			
			// Add all the folders and files to payloadFirst and payloadSecond
			File parentFolder = new File(path);
			File[] listOfFiles = parentFolder.listFiles();
			
			if (listOfFiles != null) {
				for (File currentFile : listOfFiles) {
					String name = currentFile.getName();
					
					if (currentFile.isFile()) {
						System.out.println(name + " is a file in the folder " + parentFolder.getName());
						payloadSecond += "<option value=\"" + path + "/" + name + "\">" + name + "</option>";
					} else if (currentFile.isDirectory()) {
						System.out.println(name + " is a folder in the folder " + parentFolder.getName());
						payloadFirst += "<option value=\"" + path + "/" + name + "\">" + name + "</option>";
					}
				}
				
				System.out.println();
			}
			
			// Make payload (= payloadFirst + payloadSecond + payloadThird)
			String payload = payloadFirst + payloadSecond + payloadThird;
			
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