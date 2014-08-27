import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ChatServer {

    public static void main(String[] args) throws IOException {

        ChatServer chatServer = new ChatServer();
        chatServer.go();
    }

    private void go() throws IOException {

        //Generate a random number and write to a text file

        Random randomGenerator = new Random();
        int randomInt = 1024 + randomGenerator.nextInt(64511);
        PrintWriter printWriter = new PrintWriter("port.txt");
        printWriter.write(String.valueOf(randomInt));
        printWriter.flush();

        //Create Server on a port using that random number
        ServerSocket serverSocket = new ServerSocket(randomInt);
        System.out.println("Server on Port: "+randomInt);

        AcceptClient acceptClient = new AcceptClient(serverSocket);
        Thread acceptThread = new Thread(acceptClient);

        acceptThread.start();
    }

    private class AcceptClient implements Runnable
    {
        ServerSocket serverSocket;
        Socket socket;

        public AcceptClient(ServerSocket serverSocket)
        {
            this.serverSocket = serverSocket;
        }

        public void run()
        {
            while (true)
            {
                //Accept A connection and assign a socket for this client
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Connection Established");

                ReceiveData receiveData = null;
                try {
                    receiveData = new ReceiveData(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread receiver = new Thread(receiveData);

                SendData sendData = new SendData(socket);
                Thread sender = new Thread(sendData);

                //Start Threads
                receiver.start();
                sender.start();

            }
        }
    }

    private class ReceiveData implements Runnable {

        BufferedReader bufferedReader;
        PrintWriter writer;
        Socket socket;

        public ReceiveData(Socket socket) throws IOException {

            this.socket = socket;
        }


        public void run() {

            while (true)
            {
                String message;

                try {

                    //Read Message
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);

                    while( (message = bufferedReader.readLine()) != null)
                    {

                        //Display it on the console
                        System.out.println("From Client: " + message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private class SendData implements Runnable {

        Socket socket;
        PrintWriter printWriter;

        public SendData(Socket socket) {
            this.socket = socket;
        }

        public void run()
        {


            while(true)
            {

                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                try {
                    printWriter = new PrintWriter(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                printWriter.println(input);
                printWriter.flush();

            }
        }
    }
}
