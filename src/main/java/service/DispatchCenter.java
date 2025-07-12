package service;

import model.Package;
import model.Rider;
import model.AuditEvent;

import java.util.*;
import java.util.stream.Collectors;

public class DispatchCenter {
    private final Map<String, Package> packages = new HashMap<>();
    private final Map<String, Rider> riders = new HashMap<>();
    private final List<AuditEvent> auditTrail = new ArrayList<>();

    private final PriorityQueue<Package> packageQueue = new PriorityQueue<>((a, b) -> {
        int cmp = a.getPriority().compareTo(b.getPriority()); // EXPRESS first
        if (cmp == 0) cmp = Long.compare(a.getDeliveryDeadline(), b.getDeliveryDeadline());
        if (cmp == 0) cmp = Long.compare(a.getOrderTime(), b.getOrderTime());
        return cmp;
    });

    public void placeOrder(Package pkg) {
        packages.put(pkg.getId(), pkg);
        packageQueue.add(pkg);
    }

    public void registerRider(Rider rider) {
        riders.put(rider.getId(), rider);
    }

    public void updateRiderStatus(String riderId, Rider.Status status) {
        Rider rider = riders.get(riderId);
        if (rider != null) rider.setStatus(status);
    }

    public void assignPackages() {
        while (!packageQueue.isEmpty()) {
            Package pkg = packageQueue.peek();
            Optional<Rider> availableRider = riders.values().stream()
                    .filter(r -> r.getStatus() == Rider.Status.AVAILABLE)
                    .filter(r -> !pkg.isFragile() || r.canHandleFragile())
                    .findFirst();

            if (availableRider.isPresent()) {
                Rider rider = availableRider.get();
                pkg.setAssignedRiderId(rider.getId());
                pkg.setStatus(Package.Status.ASSIGNED);
                rider.getAssignedPackages().add(pkg.getId());
                rider.setStatus(Rider.Status.ON_DELIVERY);
                auditTrail.add(new AuditEvent(pkg.getId(), Package.Status.PENDING, Package.Status.ASSIGNED, System.currentTimeMillis()));
                packageQueue.poll();
            } else {
                break; // No rider available now
            }
        }
    }

    public void completeDelivery(String packageId) {
        Package pkg = packages.get(packageId);
        if (pkg == null) return;

        Rider rider = riders.get(pkg.getAssignedRiderId());
        pkg.setStatus(Package.Status.DELIVERED);
        pkg.setDeliveryTime(System.currentTimeMillis());
        auditTrail.add(new AuditEvent(pkg.getId(), Package.Status.ASSIGNED, Package.Status.DELIVERED, pkg.getDeliveryTime()));

        if (rider != null) {
            rider.setStatus(Rider.Status.AVAILABLE);
            rider.getAssignedPackages().remove(packageId);
        }
    }

    public List<Package> getDeliveredByRiderInLast24Hours(String riderId) {
        long cutoff = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        return packages.values().stream()
                .filter(p -> p.getAssignedRiderId() != null &&
                        p.getAssignedRiderId().equals(riderId) &&
                        p.getStatus() == Package.Status.DELIVERED &&
                        p.getDeliveryTime() >= cutoff)
                .collect(Collectors.toList());
    }

    public List<Package> getMissedExpressDeliveries() {
        return packages.values().stream()
                .filter(p -> p.getPriority() == Package.Priority.EXPRESS &&
                        p.getStatus() == Package.Status.DELIVERED &&
                        p.getDeliveryTime() > p.getDeliveryDeadline())
                .collect(Collectors.toList());
    }

    public void showAuditTrail() {
        auditTrail.forEach(System.out::println);
    }
}
