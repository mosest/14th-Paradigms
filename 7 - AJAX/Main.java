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
	// 2. If there is af ile sent, then we just show a box with the file and a
	//	  button to go back to the file navigation two-box thing!
	// -------------------------------------------------------------------------------
	
	public static String constructPayload() {
		String total;
		
		// This is the HTML and JavaScript code!
		String first = "<html><head>" +
						"<script type=\"text/javascript\">" +

						"function httpPost(url, payload, callback) {" +
						"  var request = new XMLHttpRequest();" +
						"  request.onreadystatechange = function() {" +
						"	if(request.readyState == 4) {" +
						"	  if(request.status == 200)" +
						"		callback(request.responseText);" +
						"	  else" +
						"	  {" +
						"		if(request.status == 0 && request.statusText.length == 0)" +
						"		  alert(\"Request blocked by same-origin policy\");" +
						"		else" +
						"		  alert(\"Server returned status \" + request.status +" +
						"			\", \" + request.statusText);" +
						"	  }" +
						"	}" +
						"  };" +
						"  request.open('post', url, true);" +
						"  request.setRequestHeader('Content-Type'," +
						"	'application/x-www-form-urlencoded');" +
						"  request.send(payload);" +
						"}" +

						"function cb(response) {" +
						"  alert(\"The back-end server replied: \" + response);" +
						"}" +
						
						"function clickedOnFolder(str) {" +
						"	alert(\"user clicked on folder \" + str);" +
						"}" +
						
						"function clickedOnFile(str) {" +
						"	alert(\"user clicked on file \" + str);" +
						"}" +

						"function sayhi() {" +
						"  var msg = document.getElementById(\"mymessage\");" +
						"  alert(msg);" +
						"  httpPost(\"ajax_handler.html\", msg.value, cb);" +
						"}" +

						"</script>" +
						"</head><body>" +
						"  <input type=\"text\" id=\"mymessage\" value=\"hi\">" +
						"  <input type=\"button\" onclick=\"sayhi();\" value=\"send\">" +
						
						"	<table><tr><td>" +
						"	<b>Folders:</b><br>" +
						"	<select id=\"folderList\" size=\"15\" style=\"width: 280px\" ondblclick=\"clickedOnFolder(this.value)\">" +
						"		<option value=\"..\">..</option>" +
						"		<option value=\"somefolder\">somefolder</option>" +
						"		<option value=\"anotherfolder\">anotherfolder</option>" +						
						"		<option value=\"yetanotherone\">yetanotherone</option>" +
						"	</select>" +
						"	</td><td>" +
						"	<b>Files:</b><br>" +
						"	<select id=\"fileList\" size=\"15\" style=\"width: 280px\" ondblclick=\"clickedOnFile(this.value)\">" +
						"		<option value=\"somefile.txt\">somefile.txt</option>" +
						"		<option value=\"anotherfile.jpeg\">anotherfile.jpeg</option>" +
						"	</select>" +
						"	</td></tr></table>" +

						"</body></html>";
						
		
		
		// Return the full string all pieced together :3
		return total;
	}

	// -------------------------------------------------------------------------------
	// Main Function
	// -------------------------------------------------------------------------------
	
	public static void main(String[] args) throws Exception {
		// Set a path to start out with!
		String path = "./Assignment6Folders";
		
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
			}
			
			// Construct payload! :D
			String payload = constructPayload();
			
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
