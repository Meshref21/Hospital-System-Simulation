import java.util.Scanner;

public class Hospital
{
    private int WaitingRoomCapicty;
    private int NumberOfDoctors;
    private int NumberOfPatients;

    private Thread[] DoctorsThreads;
    private Thread[] PatientThreads;

    private TreatmentRooms treatmentRooms;
    private WaitingRoom waitingRoom;

    // Hospital(int waiting, int doctors, int patients)
    // {

    // }

    public static void main(String[] args)
    {
        Hospital hospital = new Hospital();

        hospital.ReadInputs();
        hospital.InitializeSystem();
        hospital.StartSimulation();
        hospital.EndSimulation();
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

        waitingRoom = new WaitingRoom(WaitingRoomCapicty);
        treatmentRooms = new TreatmentRooms();

        DoctorsThreads = new Thread[NumberOfDoctors];
        PatientThreads = new Thread[NumberOfPatients];
        
        for(int i = 0 ; i < NumberOfDoctors ; i++)
        {
            DoctorsThreads[i] = new Thread(new Doctor(i + 1, waitingRoom, treatmentRooms));
        }

        for(int i = 0 ; i < NumberOfPatients ; i++)
        {

            PatientThreads[i] = new Thread(new Patient(i + 1, waitingRoom));
        }

    }

    public void StartSimulation()
    {
        for(int i = 0 ; i < NumberOfDoctors ; i++)
        {
            DoctorsThreads[i].start();
        }

        for(int i = 0 ; i < NumberOfPatients ; i++)
        {
            PatientThreads[i].start();
        }
    }

    public void EndSimulation()
    {
        try
        {
            for(int i = 0 ; i < NumberOfPatients ; i++)
            {

                PatientThreads[i].join();
            }
            
            waitingRoom.wakeDoctors(NumberOfDoctors);

            for(int i = 0 ; i < NumberOfDoctors ; i++)
            {
                DoctorsThreads[i].join();
            }

            System.out.println("\nAll patients have been treated.");
            System.out.println("Simulation completed.");    
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

}
