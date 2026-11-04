public class Patient implements Runnable {

    private int patientId;
    private WaitingRoom waitingRoom;

    private boolean treatmentFinished = false;

    public Patient(int patientId, WaitingRoom waitingRoom) {
        this.patientId = patientId;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {

        Arrive();

        EnterWaitingRoom();

        if (WaitForTreatment()) {
            LeaveHospital();
        }
    }

    public void Arrive() {
        System.out.println(getPatientName() +
                " arrived at the hospital.");
    }

    public void EnterWaitingRoom() {
        waitingRoom.enterWaitingRoom(this);
    }

    public synchronized boolean WaitForTreatment() {

        while (!treatmentFinished) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        return true;
    }

    public synchronized void FinishTreatment() {

        treatmentFinished = true;

        notifyAll();
    }

    public void LeaveHospital() {
        System.out.println(getPatientName() +
                " left the hospital.");
    }

    public String getPatientName() {
        return "Patient " + patientId;
    }

    public int getPatientId() {
        return patientId;
    }
}