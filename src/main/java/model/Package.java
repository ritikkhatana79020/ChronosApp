package model;

public class Package {
    public enum Priority { EXPRESS, STANDARD }
    public enum Status { PENDING, ASSIGNED, PICKED_UP, DELIVERED }

    private final String id;
    private final long orderTime;
    private final long deliveryDeadline;
    private final boolean isFragile;
    private final Priority priority;
    private Status status;
    private String assignedRiderId;
    private long pickupTime;
    private long deliveryTime;

    public Package(String id, long orderTime, long deliveryDeadline, boolean isFragile, Priority priority) {
        this.id = id;
        this.orderTime = orderTime;
        this.deliveryDeadline = deliveryDeadline;
        this.isFragile = isFragile;
        this.priority = priority;
        this.status = Status.PENDING;
    }

    // Getters and Setters
    public String getId() { return id; }
    public long getOrderTime() { return orderTime; }
    public long getDeliveryDeadline() { return deliveryDeadline; }
    public boolean isFragile() { return isFragile; }
    public Priority getPriority() { return priority; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getAssignedRiderId() { return assignedRiderId; }
    public void setAssignedRiderId(String assignedRiderId) { this.assignedRiderId = assignedRiderId; }
    public void setPickupTime(long pickupTime) { this.pickupTime = pickupTime; }
    public void setDeliveryTime(long deliveryTime) { this.deliveryTime = deliveryTime; }
    public long getDeliveryTime() { return deliveryTime; }
}
