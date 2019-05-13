package Share;
import java.io.*;
import java.util.ArrayList;
import Share.*;
public class DataBase extends Thread
{
    public static File fileS = new File("SData.txt"), fileT = new File("TData.txt"), fileH = new File("HData.txt"), fileE = new File("EData.txt");
    public static ObjectOutputStream objectOutputStream;
    public static ArrayList<ChieF> chief = new ArrayList<>();
    public static ArrayList<Expert> expert = new ArrayList<>();
    public static ArrayList<Ostad> Teachers= new ArrayList<>();
    public static ArrayList<Student> Students = new ArrayList<>();
    @Override
    public void run()
    {
        try
        {
            fileS.createNewFile();
            fileT.createNewFile();
            fileE.createNewFile();
            fileH.createNewFile();
            ChieF c = new ChieF("Behrouz Shahgholi",0 ,0);
            chief.add(c);
            Expert E = new Expert("Mohsen Taheri");
            expert.add(E);
            Teachers.add(new Ostad("Behrouz Shahgholi", 0, 0, 0));
            Teachers.add(new Ostad("Houman Nikmehr", 0, 0, 0));
            Teachers.add(new Ostad("Shokoofe Kolahdooz Rahimi", 0, 0, 0));
            Teachers.add(new Ostad("Fatemeh Raji", 0, 0, 0));
            Teachers.add(new Ostad("Hamid Mala", 0, 0, 0));
            Teachers.add(new Ostad("Mohsen Taheri", 0, 0, 0));
            Teachers.add(new Ostad("Reza Pazan", 0, 0, 0));
            Teachers.add(new Ostad("Ali Kheirandish", 0, 0, 0));
            Teachers.add(new Ostad("Omid Akhgari", 0, 0, 0));
            Teachers.add(new Ostad("Ali Sajadi", 0, 0, 0));
            //People.PeopleAL.add(People.Teachers);
            Students.add(new Student("Majid Ghasemi", "BsC"));
            Students.add(new Student("Amir Nayeri", "BsC"));
            Students.add(new Student("Hamid Khosravi", "BsC"));
            Students.add(new Student("Mahdi BaniSharif", "BsC"));
            Students.add(new Student("Amir Jahanbin", "MsC"));
            Students.add(new Student("Hossein Ghojavand", "MsC"));
            Students.add(new Student("Sajad YazdanParast", "MsC"));
            Students.add(new Student("Farinam Hemati", "PhD"));
            Students.add(new Student("Niloofar Mojoodi", "PhD"));
            Students.add(new Student("Ghazaleh Shahin", "PhD"));
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileS, true));
            objectOutputStream.writeObject(Students);
            objectOutputStream.flush();
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileT, true));
            objectOutputStream.writeObject(Teachers);
            objectOutputStream.flush();
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileH, true));
            objectOutputStream.writeObject(chief);
            objectOutputStream.flush();
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileE, true));
            objectOutputStream.writeObject(expert);
            objectOutputStream.flush();
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }catch(IOException e)
        {
            e.printStackTrace();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void makeJudgeTeacher(String name)
    {
        for (int i = 0; i < Teachers.size(); i++)
        {
            if (Teachers.get(i).name.equals(name))
            {
                Teachers.get(i).isJudge = 1;
            }
        }
    }
    public void makeGuideTeacher(String name)
    {
        for (int i = 0; i < Teachers.size(); i++)
        {
            if (Teachers.get(i).name.equals(name))
            {
                Teachers.get(i).isGuide = 1;
            }
        }
    }
}
class ChieF implements Serializable
{
    int isGuide, isJudge;
    String name;
    public ChieF(String name, int isGuide, int isJudge)
    {
        this.name=name;
        this.isGuide = isGuide;
        this.isJudge = this.isJudge;
    }
}
class Expert implements Serializable
{
    String name;
    public Expert(String name)
    {
        this.name=name;
    }
}
class Ostad implements Serializable
{
    int isObserver;
    int isJudge;
    int isGuide;
    String name;
    public Ostad(String name, int isGuide, int isJudge, int isOb)
    {
        this.isObserver = isOb;
        this.name=name;
        this.isGuide = isGuide;
        this.isJudge = isJudge;
    }
    @Override
    public String toString()
    {
        return name;
    }
}
class Student implements Serializable
{
    String name, grade;
    public Student (String name, String grade)
    {
        this.grade = grade;
        this.name = name;
    }
    @Override
    public String toString()
    {
        return this.name;
    }
}