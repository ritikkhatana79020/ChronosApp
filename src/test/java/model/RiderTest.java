package model;

public class RiderTest {
    public static void main(String[] args) {
        String riderId = "rider001";
        boolean canHandleFragile = true;
        double reliability = 4.7;

        // Create Rider
        Rider rider = new Rider(riderId, canHandleFragile, reliability);

        // Test 1: Constructor and getters
        assert rider.getId().equals(riderId) : "Rider ID mismatch";
        assert rider.canHandleFragile() == true : "Fragile capability mismatch";
        assert rider.getReliabilityRating() == reliability : "Reliability rating mismatch";
        assert rider.getStatus() == Rider.Status.AVAILABLE : "Initial status should be AVAILABLE";
        assert rider.getAssignedPackages().isEmpty() : "Assigned package list should be empty initially";

        // Test 2: Set and get status
        rider.setStatus(Rider.Status.ON_DELIVERY);
        assert rider.getStatus() == Rider.Status.ON_DELIVERY : "Status update failed";

        // Test 3: Add assigned packages
        rider.getAssignedPackages().add("PKG001");
        rider.getAssignedPackages().add("PKG002");
        assert rider.getAssignedPackages().size() == 2 : "Assigned packages not added properly";
        assert rider.getAssignedPackages().contains("PKG001") : "PKG001 missing from assigned packages";

        // Output success
        System.out.println("RiderTest passed for rider ID: " + rider.getId());
    }
}
