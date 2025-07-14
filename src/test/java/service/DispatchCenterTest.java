package service;

import model.Package;
import model.Rider;
import model.AuditEvent;

import java.util.List;

public class DispatchCenterTest {

    public static void main(String[] args) throws InterruptedException {
        DispatchCenter center = new DispatchCenter();

        // Step 1: Register Riders
        Rider r1 = new Rider("r1", true, 4.8);
        Rider r2 = new Rider("r2", false, 4.5);
        center.registerRider(r1);
        center.registerRider(r2);
        center.updateRiderStatus("r1", Rider.Status.AVAILABLE);
        center.updateRiderStatus("r2", Rider.Status.AVAILABLE);

        // Step 2: Place Orders
        long now = System.currentTimeMillis();
        Package p1 = new Package("pkg1", now, now + 60000, false, Package.Priority.EXPRESS);
        Package p2 = new Package("pkg2", now, now + 60000, true, Package.Priority.STANDARD);
        center.placeOrder(p1);
        center.placeOrder(p2);

        // Step 3: Assign Packages
        center.assignPackages();
        assert p1.getStatus() == Package.Status.ASSIGNED : "Package1 should be assigned";
        assert p1.getAssignedRiderId() != null : "Package1 should have an assigned rider";

        assert p2.getStatus() == Package.Status.ASSIGNED : "Package2 should be assigned";
        assert p2.getAssignedRiderId() != null : "Package2 should have an assigned rider";

        // Step 4: Complete Delivery
        Thread.sleep(100);  // small delay to simulate delivery
        center.completeDelivery("pkg1");
        assert p1.getStatus() == Package.Status.DELIVERED : "Package1 should be delivered";

        // Step 5: Delivered in last 24h
        List<Package> deliveredByR1 = center.getDeliveredByRiderInLast24Hours("r1");
        assert deliveredByR1.size() == 1 : "Rider1 should have 1 delivered package";
        assert deliveredByR1.get(0).getId().equals("pkg1") : "Delivered package should be pkg1";

        // Step 6: Missed EXPRESS check
        List<Package> missed = center.getMissedExpressDeliveries();
        assert missed.isEmpty() : "No missed EXPRESS deliveries expected";

        // Step 7: Audit Trail Output
        System.out.println("\n=== Audit Trail ===");
        center.showAuditTrail();

        // Final result
        System.out.println("\nDispatchCenterTest passed");
    }
}
