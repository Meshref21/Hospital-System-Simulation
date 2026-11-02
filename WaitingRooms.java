public class WaitingRooms {
    private Semaphore patientToChair;
    private Semaphore doctorToPatient = new Semaphore(0);   // constant, fine as an initializer

    public WaitingRooms(int capacity) {
        this.patientToChair = new Semaphore(capacity);
    }
}