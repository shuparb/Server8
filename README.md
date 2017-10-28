# Server8
_Android HTTP server using TCP/IP protocol_

The aim is to provide server emulation inside an application. The server will host files for peers on the network. The server can serve 
websites or files depending on the protocol set on it. The network can be local network or the internet. The Hosted file / service can be 
accessed by multiple devices at a time providing real time service accessibility. 
	Servers are the other end of today’s information industry. Not only companies but lot of independent creators are coming forward to 
present their work and talent on the internet. In such situation where web hosting prices are not that cheap and the need to connect with 
the world is the need. Also many students want to share their projects, notes and study material with other colleagues, here paying monthly 
rents on server rents might not be the best opinion. 
	We thought of using our Android phones for this purpose where the user only have to pay for the domain registration and can assign 
that domain to their home IP. Then that person can port forward his mobile IP to host files on the required port. This is the most cost 
efficient way to save on server prices and also having full control over our data. User can also directly monitor his server for any server 
traffic on the go. This will give people power to share their content to anyone they want. 

## Application Components: 
1. 	User Interface - The UI is organized into different fragments controlled by menu list on the top of the activity. Following are the
menuitems and their functions: 
	1. 	ADD/REMOVE: It contains adding & removing server options. Clicking ADD will start a new screen where the user will configure
the server with its name, storage, connectivity, protocol & others. Clicking REMOVE will stop the server if running, delete storage and
	remove 
the entire server configuration. 
	2. 	SERVERS: It lists all the servers created, running & stopped. The user can toggle the server by clicking the list item
server’s name. 
	3. 	NETWORK INFO: It contains all the network information about the network where the device is connected like its local IP
address, public IP address, MAC address, traffic and other statistics.
	4.	ABOUT: It’s the application information screen which contains the application description, features, developers, updates,
bugsfixed. 
2.	Back-end – The backend contains all the memory management, client handling and content distribution. It uses different multitasking
andmultithreading technique to keep the server running, its operations and error handling. 

### Flow of operation: 
   Starting new server creates new process and objects. The server now runs on its own & handle clients in that process 
until prompt to stop by the application. Following is the operation flow of the server: 
1.	Start-up: The starting part of the server operation which will do following steps.
	1.	Create a new process & label it. 
	2.	Open the server directory in the process. 
	3.	Open the desired port at the local IP. 
	4.	Start listening for client request. 
	5.	Inform to the user.
2.	Client handling: The middle running part where the server takes request and do following steps. 
	1.	Take request from the client 
	2.	Create new client handler object which contains threads to handle content. 
	3.	Push the given content back to client.
	4.	Close the connection.
	5.	Log all the information.
	6.	Go to a. again.
3.	Stop: The end part where the server has to safely stop all the services and process as follows: 
	1.	Terminate the current client if any. 
	2.	Close all the client handler objects safely.
	3.	Close the port.
	4.	Stop the process. 
	5.	Wait for garbage collection. 
	6.	Inform to the user. 

### Future scope:
1. 	Providing future OS support 
2.	Providing cross platform compatibility with other platforms 
3.	Improving client handling algorithms for better performance
4.	Fixing bugs and crashes users reported 
5.	Hardware specific bugs fixing 
