## Multithreaded Chat Application
### Overview
A simple multithreaded chat application in Java that allows multiple clients to connect to a server and communicate in real time.

### Features
- Real-time messaging between clients
- Support for multiple clients
- Console-based user interface
- Graceful disconnection

### Technologies Used
- Java (version 17 or higher)
- Socket programming
- Multithreading

## Getting Started
### Prerequisites
- JDK 17 or higher

### Installation
- Clone the repository:
```
git clone https://github.com/neetbhalala/chat-room.git
```

- Navigate to the project directory:
```
cd chat-room
```

### Running the Application
- Compile all java class
```
javac -d out src/server/*.java src/client/*.java
```

- Start the server:
```
java -cp out server.ChatServer
```

- Start one or more clients:
```
java -cp out client.ChatClient
```

### Usage
- Type messages in the client console to send.

- Type ```/exit``` to disconnect.
