package Share;
import Share.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class GradExpert extends Thread
{
    static ArrayList<Object> slist = new ArrayList<>(), tlist = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    private static String time,Date, name,msg,Supername1,Supername2, choice;
    public static Socket socket;
    public static ObjectOutputStream outputStream ;
    public static ObjectInputStream inputStream, ois;
    public static void main(String[] args)
    {
        boolean ifExit = false;
        try
        {
            Socket socket = new Socket("localhost", 12345);
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
                System.out.println("Menu:\n1-see requests: choose Supervisor/set time and date\n2- send announcement\n3- Exit");
                choice = input.next();
                switch(choice)
                {
                    case "1":
                    {
                        chooseObserver();
                    }
                    break;
                    case "2":
                    {
                        SendAnnouncement();
                    }
                    break;
                    case "3":
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
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void setTime()
    {
        System.out.println("Enter the Date: ");
        Date = input.nextLine();
        System.out.println("Enter the time: ");
        time = input.nextLine();
        System.out.println("Announcement Setting Done!!!");
    }
    public static void chooseObserver()
    {
        try
        {
            System.out.println("Teachers' List:");
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
            System.out.println("Choose one theacher from the above list as the first observer");
            while (true)
            {
                boolean ifFound = false;
                Supername1 = input.next();
                for (int i = 0; i < tlist.size(); i++)
                {
                    if (Supername1.equals(tlist.get(i).toString()))
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
            outputStream.writeUTF(Supername1+"forOB");
            System.out.println("Choose another teacher from the above list as the second observer");
            while (true)
            {
                boolean ifFound = false;
                Supername2 = input.next();
                for (int i = 0; i < tlist.size(); i++)
                {
                    if (Supername2.equals(tlist.get(i).toString()))
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
            outputStream.writeUTF(Supername2+"forOB");
        }
        catch(IOException E)
        {
            E.printStackTrace();
        }
    }
    public static void SendAnnouncement()
    {
        try
        {
            msg = inputStream.readUTF();
            if (msg.contains("forGrad"))
            {
                msg = msg.substring(10, msg.length());
                outputStream.writeUTF("forP&M: "+msg);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void seeRequests()
    {

        try
        {
            msg = inputStream.readUTF();
            if (msg.contains("forGrad"))
            {
                chooseObserver();
                setTime();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}