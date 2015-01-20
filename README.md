Multi Threaded ChatApplication
===============

The server will run on socket with random port number everytime, and the port number is printed to an external file.
The server consists of a thread to accept the client request to connect.
The server will accept the client request and assign a socket to that client.
Two child threads(of the thread accepting requests) are created for sending/receiving text from a client

The Client sends a request to server o connect.
Then it will send/receive text to/from server through two threads.

The connection is left open between the server/client from the time they are connected.
