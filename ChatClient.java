import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    Socket socket;

    public static void main(String[] args) throws IOException {

        ChatClient chatClient = new ChatClient();
        chatClient.go();
    }

    private void go() throws IOException {

        //Read Port number from file
        FileReader fileReader = new FileReader("port.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int port = Integer.parseInt(bufferedReader.readLine());

        //Connect to socket on the port number
        System.out.println("Connecting to Server on: "+port);
        socket = new Socket("127.0.0.1",port);

        //Initiate sender thread
        ChatSender chatSender = new ChatSender(socket);
        Thread sender = new Thread(chatSender);

        //Initiate receiver thread
        ChatReceiver chatReceiver = new ChatReceiver(socket);
        Thread receiver = new Thread(chatReceiver);

        sender.start();
        receiver.start();
    }

    public class ChatSender implements Runnable
    {
        PrintWriter printWriter;

        public ChatSender(Socket socket) throws IOException {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            printWriter= new PrintWriter(outputStreamWriter);

        }

        public void run()
        {
            while (true)
            {
                //get text from console
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                //write to server
                printWriter.println(input);

                //flush the text
                printWriter.flush();
            }
        }
    }

    public class ChatReceiver implements Runnable
    {

        BufferedReader bufferedReader;

        public ChatReceiver(Socket socket) throws IOException {

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
        }

        public void run()
        {
            try {
                while (true)
                {

                    String output = bufferedReader.readLine();
                    System.out.println("From Server: " + output);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
