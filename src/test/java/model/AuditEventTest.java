package model;

public class AuditEventTest {
    public static void main(String[] args) {
        // Sample data
        String packageId = "PKG123";
        Package.Status fromStatus = Package.Status.PENDING;
        Package.Status toStatus = Package.Status.ASSIGNED;
        long timestamp = System.currentTimeMillis();

        // Create AuditEvent
        AuditEvent event = new AuditEvent(packageId, fromStatus, toStatus, timestamp);

        // Test 1: Check toString contains correct info
        String output = event.toString();
        assert output.contains(packageId) : "toString missing packageId";
        assert output.contains(fromStatus.toString()) : "toString missing 'from' status";
        assert output.contains(toStatus.toString()) : "toString missing 'to' status";
        assert output.contains(String.valueOf(timestamp)) : "toString missing timestamp";

        // Output result
        System.out.println("AuditEvent toString(): " + output);
        System.out.println("AuditEventTest passed");
    }
}
