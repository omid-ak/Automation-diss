package Share;
import static Share.Server.database;
import java.io.*;
import java.net.*;
import java.util.*;
import Share.*;
public class Server
{
    public static DataBase database = new DataBase();
    public static void main(String[] args)
    {
        database.start();
        try
        {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true)
            {
                Socket socket = serverSocket.accept();
                Process process = new Process(socket);
                process.start();
            }
        }catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
class Process extends Thread
{
    Scanner input = new Scanner(System.in);
    int choice;
    String name, inStream;
    Socket socket;
    public Process(Socket socket)
    {
        this.socket = socket;
    }
    @Override
    public void run()
    {

        try
        {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            if (!inputStream.readUTF().isEmpty())
            {
                inStream = inputStream.readUTF();
            }
            if (inStream.contains("G"))
            {
                name = inStream.substring(0, inStream.length()-1);
                database.makeGuideTeacher(name);
                outputStream.writeUTF("DONE");
            }
            else if (inStream.contains("J"))
            {
                name = inStream.substring(0, inStream.length()-1);
                database.makeJudgeTeacher(name);
                outputStream.writeUTF("DONE");
            }
            else if (inStream.contains("A"))
            {
                System.out.println(inStream.substring(0, inStream.length()-1));
            }
            else if (inStream.contains("CheckT"))
            {
                name = inStream.substring(0, inStream.length()-6);
                name = inStream.substring(0, inStream.length()-6);
                for (int i = 0; i < database.Teachers.size(); i++)
                {
                    if (database.Teachers.get(i).name.equals(name))
                    {
                        if (database.Teachers.get(i).isJudge == 1)
                        {
                            outputStream.writeUTF(name + ", You are assigned as judge.");
                        }
                        if (database.Teachers.get(i).isObserver == 1)
                        {
                            outputStream.writeUTF(name+ ", You are assigned as observer");
                        }
                    }
                }
            }
            else if (inStream.contains("forChief"))
            {
                String msg = inStream;
                outputStream.writeUTF(msg);
            }
            else if (inStream.contains("forGrad"))
            {
                String msg = inStream;
                outputStream.writeUTF(msg);
            }
            else if (inStream.contains("forOB"))
            {
                name = inStream;
                name = name.substring(0, name.length()-5);
                for (int i = 0; i < database.Teachers.size(); i++)
                {
                    if (name.equals(database.Teachers.get(i).name))
                    {
                        database.Teachers.get(i).isObserver = 1;
                    }
                }
            }
            else if (inStream.contains("forP&M"))
            {
                String msg = inStream;
                msg = msg.substring(9, msg.length());
                System.out.println("PhD and MsC Announcements:\n"+msg);
            }
        }catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
}