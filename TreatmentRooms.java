public class TreatmentRooms {
    private Semaphore treatmentRooms = new Semaphore(2);

    public boolean tryAcquireRoom(long timeoutMillis) {
        return treatmentRooms.tryAcquire(timeoutMillis);
    }

    public void releaseRoom() {
        treatmentRooms.release();
    }
}