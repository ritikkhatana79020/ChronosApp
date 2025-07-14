package test;

import model.Package;
import model.Rider;
import service.DispatchCenter;

public class ChronosAppTest {

    public static void main(String[] args) throws InterruptedException {
        DispatchCenter center = new DispatchCenter();

        // Simulate CLI command: REGISTER_RIDER r1 true 4.8
        center.registerRider(new Rider("r1", true, 4.8));
        center.updateRiderStatus("r1", Rider.Status.AVAILABLE);

        // Simulate CLI command: PLACE_ORDER pkg1 EXPRESS <deadline> true
        long now = System.currentTimeMillis();
        long deadline = now + 10000;
        Package pkg = new Package("pkg1", now, deadline, true, Package.Priority.EXPRESS);
        center.placeOrder(pkg);

        // Simulate CLI command: ASSIGN
        center.assignPackages();

        assert pkg.getStatus() == Package.Status.ASSIGNED : "pkg1 should be ASSIGNED";
        assert pkg.getAssignedRiderId().equals("r1") : "pkg1 should be assigned to rider r1";

        // Simulate CLI command: COMPLETE_DELIVERY pkg1
        Thread.sleep(100);  // simulate delay
        center.completeDelivery("pkg1");

        assert pkg.getStatus() == Package.Status.DELIVERED : "pkg1 should be DELIVERED";

        // Simulate CLI command: REPORT_RIDER r1
        System.out.println("\n Packages delivered by r1 in last 24 hours:");
        center.getDeliveredByRiderInLast24Hours("r1").forEach(p -> System.out.println(p.getId()));

        // Simulate CLI command: REPORT_MISSED
        System.out.println("\n Missed EXPRESS deliveries:");
        center.getMissedExpressDeliveries().forEach(p -> System.out.println(p.getId()));

        // Simulate CLI command: AUDIT
        System.out.println("\n Audit Trail:");
        center.showAuditTrail();

        System.out.println("\n ChronosAppTest completed successfully");
    }
}
