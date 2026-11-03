import java.util.Scanner;

public class Hospital
{
    private int WaitingRoomCapicty;
    private int NumberOfDoctors;
    private int NumberOfPatients;

    private Thread[] DoctorsThreads;
    private Thread[] PatientThreads;

    public static void main(String[] args)
    {
        Hospital hospital = new Hospital();
        hospital.ReadInputs();
        hospital.InitializeSystem();
    }

    public void ReadInputs()
    {

        Scanner Input = new Scanner(System.in);

        System.out.println("Please, Enter The Waiting Room Capicty: ");
        WaitingRoomCapicty = Input.nextInt();

        System.out.println("Please, Enter the Number Of Doctors: ");
        NumberOfDoctors = Input.nextInt();

        System.out.println("Please, Enter The Number Of Patients: ");
        NumberOfPatients = Input.nextInt();

        Input.close();

    }   
    
    public void InitializeSystem()
    {
        
        for(int i = 0 ; i < NumberOfDoctors ; i++)
        {
            DoctorsThreads[i] = new Thread(new Doctor());
        }

        for(int i = 0 ; i < NumberOfPatients ; i++)
        {
            PatientThreads[i] = new Thread(new Patient());
        }

    }

    public void PrintLogEvent()
    {
        // Displaying all simulation events
    }

}
