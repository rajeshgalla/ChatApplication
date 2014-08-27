import java.net.*;
import java.io.*;

public class bankserver extends Thread
{
	private ServerSocket connectionSocket;
	
	String[] Account={"CITI100","CITI522","CITI434","CITI654","CITI767"};
	int[] Money={1000,6544,3456,8742,7675};
	
	

	public bankserver(int port) throws IOException
	{
		connectionSocket = new ServerSocket(port);
		connectionSocket.setSoTimeout(10000000);
	}

	public void run() 
		{
		while(true)
		{
			try
			{
				System.out.println("\nWaiting for connection");
				Socket server = connectionSocket.accept();
				System.out.println("Connected to a client\n");
				DataInputStream in = new DataInputStream(server.getInputStream());
				
				String check = in.readUTF();
				System.out.println("Operation is " + check);
				
				if(check.equals("inquiry"))
				{
					DataInputStream accnt = new DataInputStream(server.getInputStream());
					float y = inquiry(accnt.readUTF());
					OutputStream os = server.getOutputStream();
					DataOutputStream out = new DataOutputStream(os);
					if(y==-1)
					{
						out.writeUTF(check + " is invalid");
					}
					else
					{
						out.writeUTF("The current balance of user "+ check + "is Rs. " + y +" only");
					}
				}
				else
					if(check.equals("deposit"))
					{
						DataInputStream accnt = new DataInputStream(server.getInputStream());
						DataInputStream amt = new DataInputStream(server.getInputStream());
						
						String inString = accnt.readUTF();
						int inInt = Integer.parseInt(amt.readUTF());
						
						OutputStream os = server.getOutputStream();
						DataOutputStream out = new DataOutputStream(os);
						
						boolean x = deposit(inString,inInt);
						if(x == true)
						{
							out.writeUTF("Successfully deposited Rs. "+inInt+" to "+inString);
						}
						else
						{
							out.writeUTF("deposited of Rs. "+inInt+" to "+inString+ " unsuccessful");
						}
					}
					else
						if(check.equals("withdraw"))
						{	
							DataInputStream accnt = new DataInputStream(server.getInputStream());
							DataInputStream amt = new DataInputStream(server.getInputStream());
							
							String inString = accnt.readUTF();
							int inInt = Integer.parseInt(amt.readUTF());
							
							float x = withdraw(inString,inInt);
							
							if( x == -1)
							{
								DataOutputStream out = new DataOutputStream(server.getOutputStream());
								out.writeUTF("trying to withdraw more");
							}
							else if( x == -2)
							{
								DataOutputStream out = new DataOutputStream(server.getOutputStream());
								out.writeUTF("account doesn't exist");
							}
							else
							{
								DataOutputStream out = new DataOutputStream(server.getOutputStream());
								out.writeUTF("Successfully withdraw "+ x + " from " + inString);
							}
						}
						
				server.close();
				System.out.println("\nConnection closed\n\n");
			}
			catch(SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				break;
			}
			catch(IOException e)
			{
				e.printStackTrace();
			break;
			}
		}
	}
	public static void main(String [] args) throws Exception
	{
		int port = Integer.parseInt(args[0]);
		try
		{
			Thread t = new bankserver(port);
			t.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	boolean deposit(String accnt, int amt)
	{
		boolean returnBool;
		returnBool = false;
		if(accnt.equals(Account[0])){
			Money[0]=Money[0]+amt;
			returnBool = true;
		}
		else if(accnt.equals(Account[1])){
			Money[1]=Money[1]+amt;
			returnBool = true;
		}
		else if(accnt.equals(Account[2])){
			Money[2]=Money[2]+amt;
			returnBool = true;
		}
		else if(accnt.equals(Account[2])){
			Money[3]=Money[3]+amt;
			returnBool = true;
		}
		else if(accnt.equals(Account[2])){
			Money[4]=Money[4]+amt;
			returnBool = true;
		}
		return returnBool;
	}
	
	float withdraw(String accnt, int amt)
	{
		float newBal;
		if( (accnt.equals(Account[0])))
		{
			if(Money[0] <amt)
			{
				return -1;
			}
			Money[0]=Money[0]-amt;
		}
		else if( (accnt.equals(Account[1])))
		{
			if(Money[1] < amt)
			{
				return -1;
			}
			Money[1]=Money[1]-amt;
		}
		else if( (accnt.equals(Account[2])))
		{
			if(Money[2] < amt)
			{
				return -1;
			}
			Money[2]=Money[2]-amt;
		}
		else if( (accnt.equals(Account[3])))
		{
			if(Money[3] < amt)
			{
				return -1;
			}
			Money[3]=Money[3]-amt;
		}
		else if( (accnt.equals(Account[4])))
		{
			if(Money[4] < amt)
			{
				return -1;
			}
			Money[4]=Money[4]-amt;
		}
		else
		{
			
			return -2;
		}
		return amt;
	}
	
	float inquiry(String accnt)
	{
		int i;
		float returnFloat = -1;
		for(i=0;i<5;i++)
		{
			if(accnt.equals(Account[i]))
			{
				returnFloat = Money[i];
			}
		}
		return returnFloat;
	}
}



