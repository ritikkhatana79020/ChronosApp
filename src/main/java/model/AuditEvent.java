package model;

public class AuditEvent {
    private final String packageId;
    private final Package.Status from;
    private final Package.Status to;
    private final long timestamp;

    public AuditEvent(String packageId, Package.Status from, Package.Status to, long timestamp) {
        this.packageId = packageId;
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Package " + packageId + ": " + from + " → " + to + " @ " + timestamp;
    }
}
