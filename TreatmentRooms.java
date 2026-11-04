public class TreatmentRooms {

    private Semaphore treatmentRooms;

    
    public TreatmentRooms() {
        treatmentRooms = new Semaphore(2);
    }

    
    public boolean tryAcquireRoom() {

        return treatmentRooms.tryAcquire(3000);
    }

    
    public void releaseRoom() {

        treatmentRooms.release();
    }
}