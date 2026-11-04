import java.util.Random;

public class Doctor implements Runnable {

    private final int doctorId;
    private final WaitingRoom waitingRoom;
    private final TreatmentRooms treatmentRooms;
    private final Random random = new Random();

    private Patient currentPatient;

    public Doctor(int doctorId, WaitingRoom waitingRoom, TreatmentRooms treatmentRooms) {
        this.doctorId = doctorId;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    @Override
    public void run() {
        while (true) {
            WaitForPatient();
            if (currentPatient == null) {
                break;
            }
            AssignPatient();

            if (TryAcquireTreatmentRoom()) {
                BeginTreatment();
                SimulateTreatment();
                FinishTreatment();
            } else {
                SendBackToWaitingRoom();
            }

            System.out.println("Doctor " + doctorId + " is now available");
        }
    }

    public void WaitForPatient() {
        currentPatient = waitingRoom.getNextPatient();
    }

    public void AssignPatient() {
        System.out.println("Doctor " + doctorId + " is assigned to" + currentPatient.getPatientName());
    }

    public boolean TryAcquireTreatmentRoom() {
        System.out.println("Doctor " + doctorId + " is trying to acquire a treatment");

        boolean acquired = treatmentRooms.tryAcquireRoom();

        if (acquired) {
            System.out.println("Doctor " + doctorId + " acquired a treatment room");
        } else {
            System.out.println("Doctor " + doctorId + " could not acquire a treatmen room");
        }
        return acquired;
    }

    public void BeginTreatment() {
        System.out.println("Doctor " + doctorId + " begins treating" + currentPatient.getPatientName());
        
    }

    public void SendBackToWaitingRoom() {
        System.out.println("Doctor " + doctorId + " is sending " + currentPatient.getPatientName() + " back to the waiting room");
        waitingRoom.enterWaitingRoom(currentPatient);
        currentPatient = null;
    }
    
    public void SimulateTreatment() {
        try {
            Thread.sleep(1000 + random.nextInt(3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void FinishTreatment() {
        System.out.println("Doctor " + doctorId + " finished treating " + currentPatient.getPatientName());

        treatmentRooms.releaseRoom();
        System.out.println("Doctor " + doctorId + " released the treatment room");

        currentPatient.FinishTreatment();  
        currentPatient = null;
    }
}