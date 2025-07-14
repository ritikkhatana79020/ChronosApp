package model;

public class PackageTest {
    public static void main(String[] args) {
        String packageId = "PKG001";
        long orderTime = System.currentTimeMillis();
        long deadline = orderTime + 3600000; // +1 hour
        boolean isFragile = true;
        Package.Priority priority = Package.Priority.EXPRESS;

        // Create Package instance
        Package pkg = new Package(packageId, orderTime, deadline, isFragile, priority);

        // Test 1: Check initial values
        assert pkg.getId().equals(packageId) : "ID mismatch";
        assert pkg.getOrderTime() == orderTime : "Order time mismatch";
        assert pkg.getDeliveryDeadline() == deadline : "Deadline mismatch";
        assert pkg.isFragile() : "Fragile mismatch";
        assert pkg.getPriority() == Package.Priority.EXPRESS : "Priority mismatch";
        assert pkg.getStatus() == Package.Status.PENDING : "Initial status should be PENDING";
        assert pkg.getAssignedRiderId() == null : "Rider ID should be null initially";
        assert pkg.getDeliveryTime() == 0 : "Delivery time should be 0 initially";

        // Test 2: Set and get status
        pkg.setStatus(Package.Status.ASSIGNED);
        assert pkg.getStatus() == Package.Status.ASSIGNED : "Status update failed";

        // Test 3: Set and get assigned rider
        pkg.setAssignedRiderId("rider123");
        assert pkg.getAssignedRiderId().equals("rider123") : "Rider assignment failed";

        // Test 4: Set and get pickup/delivery time
        long pickup = orderTime + 10000;
        long delivery = orderTime + 30000;
        pkg.setPickupTime(pickup);
        pkg.setDeliveryTime(delivery);
        assert pkg.getDeliveryTime() == delivery : "Delivery time mismatch";

        // Summary
        System.out.println("PackageTest passed for package ID: " + pkg.getId());
    }
}
