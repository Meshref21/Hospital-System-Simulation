import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final Queue<Patient> patientQueue;
    private final Semaphore spaces;
    private final Semaphore patients;
    private final Semaphore mutex;

    public WaitingRoom(int capacity) {
        this.patientQueue = new LinkedList<>();
        this.spaces = new Semaphore(capacity);
        this.patients = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void enterWaitingRoom(Patient patient) {
        if (!spaces.tryAcquire(0)) {
            System.out.println("The waiting room is full.");
            System.out.println(patient.getPatientName() + " is waiting outside for an available seat.");
            spaces.acquire();
        }
        mutex.acquire();
        patientQueue.add(patient);
        System.out.println(patient.getPatientName() + " entered the waiting room.");
        mutex.release();
        patients.release();
    }

    public Patient getNextPatient() {
        patients.acquire();
        mutex.acquire();
        Patient patient = patientQueue.poll();
        mutex.release();
        if (patient != null) {
            spaces.release();
        }
        return patient;
    }

    public void wakeDoctors(int numberOfDoctors) {
        for (int i = 0; i < numberOfDoctors; i++) {
            patients.release();
        }
    }
}
