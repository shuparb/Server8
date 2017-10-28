# Server8
Android HTTP server using TCP/IP protocol

The aim is to provide server emulation inside an application. The server will host files for peers on the network. The server can serve websites or files depending on the protocol set on it. The network can be local network or the internet. The Hosted file / service can be accessed by multiple devices at a time providing real time service accessibility. 
	Servers are the other end of today’s information industry. Not only companies but lot of independent creators are coming forward to present their work and talent on the internet. In such situation where web hosting prices are not that cheap and the need to connect with the world is the need. Also many students want to share their projects, notes and study material with other colleagues, here paying monthly rents on server rents might not be the best opinion. 
	We thought of using our Android phones for this purpose where the user only have to pay for the domain registration and can assign that domain to their home IP. Then that person can port forward his mobile IP to host files on the required port. This is the most cost efficient way to save on server prices and also having full control over our data. User can also directly monitor his server for any server traffic on the go. This will give people power to share their content to anyone they want. 
Future scope: 
•	Providing future OS support 
•	Providing cross platform compatibility with other platforms 
•	Improving client handling algorithms for better performance
•	Fixing bugs and crashes users reported 
•	Hardware specific bugs fixing 

FLOW OF OPERATION: 
User Interface - The UI is organized into different fragments controlled by menu list on the top of the activity. Following are the menu items and their functions: 
	ADD/REMOVE: It contains adding & removing server options. Clicking ADD will start a new screen where the user will configure the server with its name, storage, connectivity, protocol & others. Clicking REMOVE will stop the server if running, delete storage and remove the entire server configuration. 
	SERVERS: It lists all the servers created, running & stopped. The user can toggle the server by clicking the list item server’s name. 
	NETWORK INFO: It contains all the network information about the network where the device is connected like its local IP address, public IP address, MAC address, traffic and other statistics.
	ABOUT: It’s the application information screen which contains the application description, features, developers, updates, bugs fixed. 
Back-end – The backend contains all the memory management, client handling and content distribution. It uses different multitasking and multithreading technique to keep the server running, its operations and error handling. 
Flow of operation – Starting new server creates new process and objects. The server now runs on its own & handle clients in that process until prompt to stop by the application. Following is the operation flow of the server: 
i.	Start-up: The starting part of the server operation which will do following steps.
a.	Create a new process & label it. 
b.	Open the server directory in the process. 
c.	Open the desired port at the local IP. 
d.	Start listening for client request. 
e.	Inform to the user.
ii.	Client handling: The middle running part where the server takes request and do following steps. 
a.	Take request from the client 
b.	Create new client handler object which contains threads to handle content. 
c.	Push the given content back to client.
d.	Close the connection.
e.	Log all the information.
f.	Go to a. again.
iii.	Stop: The end part where the server has to safely stop all the services and process as follows: 
a.	Terminate the current client if any. 
b.	Close all the client handler objects safely.
c.	Close the port.
d.	Stop the process. 
e.	Wait for garbage collection. 
f.	Inform to the user. 
