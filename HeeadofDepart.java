package Share;
import Share.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
public class HeeadofDepart
{
    static ArrayList<Object> slist = new ArrayList<>(), tlist = new ArrayList<>();
    static Socket socket;
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream, ois;
    static Scanner input = new Scanner(System.in);
    static String msg,name1,name2,name3, choice;
    public static void main(String[] args)
    {
        boolean ifExit = false;
        try
        {
            socket = new Socket("localhost",1002);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("!!::Welcome to the System::!!");
            try
            {
                TimeUnit.SECONDS.sleep(2);
            }catch(InterruptedException e)
            {
                System.out.println("Wait!!!");
            }
            while(true)
            {
                System.out.println("Menu:\n1- see requests and choose judges\n2- Exit");
                choice = input.next();
                switch (choice)
                {
                    case "1":
                    {
                        seeRequests();
                    }
                    break;
                    case "2":
                    {
                        System.out.println("You successfully loged out of the system!!");
                        ifExit = true;
                    }
                    break;
                }
                if (ifExit)
                {
                    break;
                }
            }
        }catch (IOException exception)
        {
            System.out.println("Exception in Teacher Class in Socket!!");
        }
    }
    public static void choosejudge()
    {
        try
        {
            try
            {
                if (DataBase.fileT.length() == 0)
                {
                    System.out.println("File is empty.");
                }
                ois = new ObjectInputStream(new FileInputStream(DataBase.fileT));
                Object object = ois.readObject();
                tlist = (ArrayList<Object>) object;
                for (int i = 0; i < tlist.size(); i++)
                {
                    System.out.println(tlist.get(i).toString()+",");
                }
            } catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            if (msg.contains("BsC"))
            {
                System.out.println("Choose one of the teachers in the above list as the judge: ");
                while (true)
                {
                    boolean ifFound = false;
                    name1 = input.next();
                    for (int i = 0; i < tlist.size(); i++)
                    {
                        if (name1.equals(tlist.get(i).toString()))
                        {
                            ifFound = true;
                            break;
                        }
                        if (i == tlist.size()-1)
                        {
                            System.out.println("You Entered a wrong name!!\nEnter again: ");
                        }
                    }
                    if (ifFound)
                    {
                        break;
                    }
                }
                outputStream.writeUTF(name1+"J");
                System.out.println(inputStream.readUTF());
            }
            else if(msg.contains("MsC"))
            {
                System.out.println("Choose 2 names from teachers' list above as judges: ");
                while (true)
                {
                    boolean ifFound = false;
                    name1 = input.next();
                    for (int i = 0; i < tlist.size(); i++)
                    {
                        if (name1.equals(tlist.get(i).toString()))
                        {
                            ifFound = true;
                            break;
                        }
                        if (i == tlist.size()-1)
                        {
                            System.out.println("You Entered a wrong name!!\nEnter again: ");
                        }
                    }
                    if (ifFound)
                    {
                        break;
                    }
                }
                outputStream.writeUTF(name1+"J");
                while (true)
                {
                    boolean ifFound = false;
                    name2 = input.next();
                    for (int i = 0; i < tlist.size(); i++)
                    {
                        if (name2.equals(tlist.get(i).toString()))
                        {
                            ifFound = true;
                            break;
                        }
                        if (i == tlist.size()-1)
                        {
                            System.out.println("You Entered a wrong name!!\nEnter again: ");
                        }
                    }
                    if (ifFound)
                    {
                        break;
                    }
                }
                outputStream.writeUTF(name2+"J");
                System.out.println(inputStream.readUTF());
            }
            else if(msg.contains("PhD"))
            {
                System.out.println("Choose 3 names from teachers' list above as judges: ");
                while (true)
                {
                    boolean ifFound = false;
                    name1 = input.next();
                    for (int i = 0; i < tlist.size(); i++)
                    {
                        if (name1.equals(tlist.get(i).toString()))
                        {
                            ifFound = true;
                            break;
                        }
                        if (i == tlist.size()-1)
                        {
                            System.out.println("You Entered a wrong name!!\nEnter again: ");
                        }
                    }
                    if (ifFound)
                    {
                        break;
                    }
                }
                outputStream.writeUTF(name1+"J");
                while (true)
                {
                    boolean ifFound = false;
                    name2 = input.next();
                    for (int i = 0; i < tlist.size(); i++)
                    {
                        if (name2.equals(tlist.get(i).toString()))
                        {
                            ifFound = true;
                            break;
                        }
                        if (i == tlist.size()-1)
                        {
                            System.out.println("You Entered a wrong name!!\nEnter again: ");
                        }
                    }
                    if (ifFound)
                    {
                        break;
                    }
                }
                outputStream.writeUTF(name2+"J");
                while (true)
                {
                    boolean ifFound = false;
                    name3 = input.next();
                    for (int i = 0; i < tlist.size(); i++)
                    {
                        if (name3.equals(tlist.get(i).toString()))
                        {
                            ifFound = true;
                            break;
                        }
                        if (i == tlist.size()-1)
                        {
                            System.out.println("You Entered a wrong name!!\nEnter again: ");
                        }
                    }
                    if (ifFound)
                    {
                        break;
                    }
                }
                outputStream.writeUTF(name3+"J");
                System.out.println(inputStream.readUTF());
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void seeRequests()
    {
        try
        {
            if (inputStream.readUTF().contains("forChief"))
            {
                msg = inputStream.readUTF();
            }
            System.out.println(msg);
            choosejudge();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}