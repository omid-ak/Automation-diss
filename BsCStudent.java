package Share;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import Share.*;
import java.util.ArrayList;
public class BsCStudent
{
    static ArrayList<Object> slist = new ArrayList<>(), tlist = new ArrayList<>();
    //static int choice;
    static Scanner input = new Scanner(System.in);
    static String time,date, day, topic, gist, Tname, Sname, Grade, choice;
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream, ois;
    public static void main(String[] args)
    {
        boolean ifExit = false;
        try {
            Socket socket = new Socket("localhost", 12345);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e)
        {
            System.out.println("Exception occurred!!");
        }
        System.out.println("!!::Welcome to the System::!!");
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e)
        {
            System.out.println("Wait!!!");
        }
        System.out.println("Choose Your name from list below: ");
        try
        {
            if (DataBase.fileS.length() == 0)
            {
                System.out.println("File is empty.");
            }
            ois = new ObjectInputStream(new FileInputStream(DataBase.fileS));
            Object object = ois.readObject();
            slist = (ArrayList<Object>) object;
            for (int i = 0; i < slist.size(); i++)
            {
                System.out.println(slist.get(i).toString()+",");
            }
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        while (true)
        {
            boolean ifFound = false;
            Sname = input.nextLine();
            for (int i = 0; i < slist.size(); i++)
            {
                if (Sname.toLowerCase().equals(slist.get(i).toString().toLowerCase()))
                {
                    ifFound = true;
                    break;
                }
                if (i == slist.size()-1)
                {
                    System.out.println("You Entered a wrong name!!\nEnter again: ");
                }
            }
            if (ifFound)
            {
                break;
            }
        }
        while(true)
        {
            System.out.println("Menu:\n1- set Request\n2- choose GuideTeacher\n3- set Time\n4- show announcement\n5- Exit");
            choice = input.next();
            switch(choice)
            {
                case "1":
                {
                    setRequest();
                }
                break;
                case "2":
                {
                    chooseGuideTeacher();
                }
                break;
                case "3":
                {
                    setTime();
                }
                break;
                case "4":
                {
                    showAnnouncement();
                }
                break;
                case "5":
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
            choice = null;
        }
    }
    public static void setRequest()
    {
        System.out.println("In order to set your request enter your topic below: ");
        topic = input.next();
        System.out.println("Now enter a summary: ");
        gist = input.next();
        try
        {
            Grade = "BsC";
            File file = new File("SummaryBSC.txt");
            if (!(file.exists())) {file.createNewFile();}
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Sname+"\n");
            bw.write(Grade);
            bw.write(topic + ":\n");
            bw.write(gist);
            bw.flush();
            System.out.println("You successfully registered your request!!");
        } catch (IOException e)
        {
            System.out.println("Exception Occured!!");
            e.printStackTrace();
        }
    }
    public static void chooseGuideTeacher()
    {
        System.out.println("Here's a list of teachers,\nChoose one of them as your guideTeacher: ");
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
            while (true)
            {
                boolean ifFound = false;
                for (int i = 0; i < tlist.size(); i++)
                {
                    Tname = input.next();
                    if (Tname.toLowerCase().equals(tlist.get(i).toString().toLowerCase()))
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
            outputStream.writeUTF(Tname+"G");
            outputStream.flush();
            System.out.println(inputStream.readUTF());
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void setTime()
    {
        System.out.println("Enter the date below: ");
        date = input.next();
        System.out.println("Now enter the day below: ");
        day = input.next();
        System.out.println("Enter time now: ");
        time = input.next();
    }

    public static void showAnnouncement()
    {
        try {
            outputStream.writeUTF("Defence Info:\nDefender-> "+Sname+"\nTopic-> "+topic+"\nSupervisor-> "+Tname+"\nTime and Date-> "+day+", "+date+", "+time+".");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}