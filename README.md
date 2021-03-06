<h1 align="center">
  LAN Socket Chat App
</h1>
<p align="center">A group-based chat application using Java and Socket.</p>

## ⚡️ Quick start

First of all, [download](https://java.com/en/download/help/download_options.html) and install **Java**. Version `8.0` or higher is required, along with [NetBeans](https://netbeans.apache.org/download/index.html). 

Once both are installed, download the repository and open it using NetBeans.

### ServerPanel GUI
From the `Server` folder launch `ServerPanel.java`. The GUI provides information about server port, connected users, connection requests, messages, and disconnected users.

![ServerPanel GUI](https://github.com/KSJaay/LAN-Chat-App/blob/main/.github/Server.JPG)

### User connect GUI
Once the server is live, users can connect using the `GUI.java` from the `Client` folder. This GUI allows the users to input an ID (must be unique) and name, then connect to the server.

![User connect GUI](https://github.com/KSJaay/LAN-Chat-App/blob/main/.github/ConnectingUser.JPG)

### User connected GUI
After that users can use the application to send messages to all users connected to the server.

![User connected GUI](https://github.com/KSJaay/LAN-Chat-App/blob/main/.github/ActiveUser.JPG)

## Data Architecture Diagram
![Data Architecture Diagram](https://github.com/KSJaay/LAN-Chat-App/blob/main/.github/JavaDataArc.png)
