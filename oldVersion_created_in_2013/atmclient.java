import java.net.*;
import java.io.*;

public class atmclient
{
	public static void main(String [] args) throws Exception
	{
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		try
		{
			System.out.println("Connecting to server");
			Socket client = new Socket(serverName, port);
			System.out.println("Just connected to server");
			
			//Sending type of operation
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(args[2]);
			
			
			if(args[2].equals("inquiry"))
			{
				out.writeUTF(args[3]);
				InputStream is = client.getInputStream();
				DataInputStream	input = new DataInputStream(is);
				System.out.println(input.readUTF());
				
			}
			else
				if(args[2].equals("deposit") || args[2].equals("withdraw"))
				{
					out.writeUTF(args[3]);
					out.writeUTF(args[4]);
					InputStream is = client.getInputStream();
					DataInputStream	input = new DataInputStream(is);
					System.out.println(input.readUTF());
				}				
			
			
			client.close();
			System.out.println("Connection closed");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}