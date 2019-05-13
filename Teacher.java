package Share;
import Share.*;;
import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
public class Teacher
{
    static ArrayList<Object> slist = new ArrayList<>(), tlist = new ArrayList<>();
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream, ois;
    static FileReader fileReaderBSC, fileReaderMSC, fileReaderPHD;
    static Scanner input = new Scanner(System.in);
    static String name,msg, choice;
    static File fileBsC = new File("SummaryBSC.txt"),fileMsC = new File("SummaryMSC.txt"), filePhD = new File("SummaryPHD.txt");
    public static void main(String[] args)
    {
        boolean ifExit = false;
        try
        {
            Socket socket = new Socket("localhost",12345);
            fileBsC.createNewFile();
            fileMsC.createNewFile();
            filePhD.createNewFile();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            fileReaderBSC =  new FileReader("SummaryBSC.txt");
            fileReaderMSC = new FileReader("SummaryMSC.txt");
            fileReaderPHD = new FileReader("summaryPHD.txt");
            System.out.println("!!::Welcome to the System::!!");
            try
            {
                TimeUnit.SECONDS.sleep(2);
            }catch(InterruptedException e)
            {
                System.out.println("Wait!!!");
            }
            System.out.println("choose your name from list below: ");
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
            while (true)
            {
                boolean ifFound = false;
                name = input.nextLine();
                for (int i = 0; i < tlist.size(); i++)
                {
                    if (name.equals(tlist.get(i).toString()))
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
            while (true)
            {
                System.out.println("Menu:\n1- see requests: decline/accept\n2- send request to Chief\n3- Messages(Judge/Observer assignment)\n4- Exit");
                choice = input.next();
                switch(choice)
                {
                    case "1":
                    {
                        seeRequests();
                    }
                    break;
                    case "2":
                    {
                        sendRequest();
                    }
                    break;
                    case "3":
                    {
                        receiveMessage();
                    }
                    break;
                    case "4":
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
            exception.printStackTrace();
        }
    }
    public static void seeRequests()
    {
        try
        {
            if (BsCStudent.Grade.contains("BSC"))
            {
                System.out.println("You Reacieved a Reaquest from "+BsCStudent.Sname);
                fileReaderBSC.read();
            }
            if(MsCStudent.Grade.contains("MSC"))
            {
                System.out.println("You Reacieved a Request from "+MsCStudent.Sname);
                fileReaderMSC.read();
            }
            if (PhDStudent.Grade.equals("PHD"))
            {
                System.out.println("You Reacieved a reaquest from "+PhDStudent.Sname);
                fileReaderPHD.read();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void sendRequest()
    {

        try
        {
            outputStream.writeUTF("forChief: " + name);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void receiveMessage()
    {
        try
        {
            outputStream.writeUTF(name+"CheckT");
            String msg = inputStream.readUTF();
            if (msg.contains(name))
            {
                System.out.println(inputStream.readUTF());
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}