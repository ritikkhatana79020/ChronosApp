package model;

import java.util.ArrayList;
import java.util.List;

public class Rider {
    public enum Status { AVAILABLE, ON_DELIVERY, OFFLINE }

    private final String id;
    private final boolean canHandleFragile;
    private final double reliabilityRating;
    private Status status;
    private final List<String> assignedPackages = new ArrayList<>();

    public Rider(String id, boolean canHandleFragile, double reliabilityRating) {
        this.id = id;
        this.canHandleFragile = canHandleFragile;
        this.reliabilityRating = reliabilityRating;
        this.status = Status.AVAILABLE;
    }

    public String getId() { return id; }
    public boolean canHandleFragile() { return canHandleFragile; }
    public double getReliabilityRating() { return reliabilityRating; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public List<String> getAssignedPackages() { return assignedPackages; }
}
