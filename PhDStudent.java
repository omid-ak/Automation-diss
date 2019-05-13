package Share;
import Share.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public abstract class PhDStudent
{
    static ArrayList<Object> slist = new ArrayList<>(), tlist = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static String time,topic, gist, Tname, Sname, Grade, choice;
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream, ois;
    public static void main(String[] args)
    {
        boolean ifExit = false;
        try
        {
            Socket socket = new Socket("localhost", 1002);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e)
        {
            System.out.println("Exception occurred!!");
        }
        System.out.println("!!:: Welcome to The System::!!");
        try
        {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException e)
        {
            System.out.println("Wait!!");
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
        while (true)
        {
            System.out.println("Menu:\n1- set Request\n2- choose GuideTeacher\n3- receive announcement\n4- Exit");
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
                    receivetime();
                }
                break;
                case "4":
                {
                    System.out.println("You successfully logged out the system!!");
                    ifExit = true;
                }
                break;
            }
            if (ifExit)
            {
                break;
            }
        }
    }
    public static void setRequest()
    {
        System.out.println("In order to set your request enter your topic below: ");
        topic = input.next();
        System.out.println("Now enter a summary: ");
        try
        {
            Grade = "PhD";
            File file = new File("SummaryPHD.txt"); // create a file, name it in the name of the student
            if(!(file.exists())){file.createNewFile();}
            FileWriter fw = new FileWriter(file,true);
            fw.write(Sname);
            fw.write(Grade);
            fw.write(topic + ":\n");
            fw.write(input.next());
            fw.flush();
            fw.close();
        } catch (IOException e)
        {
            System.out.println("Exception Occured!!");
            e.printStackTrace();
        }
    }
    public static void chooseGuideTeacher()
    {
        System.out.println("Here's a list of teachers,\nChoose one of them as your guideTeacher: ");  //more than 1...
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
                Tname = input.next();
                for (int i = 0; i < slist.size(); i++)
                {
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
            System.out.println(inputStream.readUTF());
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void receivetime()
    {
        try
        {
            String msg = inputStream.readUTF();
            if (msg.contains("forPHD"))
            {
                msg = msg.substring(9, msg.length());
                time = msg;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void showAnnouncement()
    {
        try {
            outputStream.writeUTF("Defence Info:\nDefender-> "+Sname+"\nTopic-> "+topic+"\nSupervisor-> "+Tname+"\nTime and Date-> "+time);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void sendRequest()
    {
        try
        {
            outputStream.writeUTF("forGrad: "+Sname + Tname + topic);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}