import model.Package;
import model.Rider;
import service.DispatchCenter;

import java.util.Scanner;

public class ChronosApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DispatchCenter center = new DispatchCenter();

        System.out.println("Chronos Couriers CLI - Type HELP for commands");

        while (true) {
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            String command = tokens[0];

            try {
                switch (command) {
                    case "PLACE_ORDER":
                        String pid = tokens[1];
                        Package.Priority priority = Package.Priority.valueOf(tokens[2].toUpperCase());
                        long deadline = Long.parseLong(tokens[3]);
                        boolean fragile = Boolean.parseBoolean(tokens[4]);
                        center.placeOrder(new Package(pid, System.currentTimeMillis(), deadline, fragile, priority));
                        break;
                    case "REGISTER_RIDER":
                        String rid = tokens[1];
                        boolean canHandleFragile = Boolean.parseBoolean(tokens[2]);
                        double rating = Double.parseDouble(tokens[3]);
                        center.registerRider(new Rider(rid, canHandleFragile, rating));
                        break;
                    case "UPDATE_RIDER_STATUS":
                        center.updateRiderStatus(tokens[1], Rider.Status.valueOf(tokens[2].toUpperCase()));
                        break;
                    case "ASSIGN":
                        center.assignPackages();
                        break;
                    case "COMPLETE_DELIVERY":
                        center.completeDelivery(tokens[1]);
                        break;
                    case "REPORT_RIDER":
                        center.getDeliveredByRiderInLast24Hours(tokens[1]).forEach(p -> System.out.println(p.getId()));
                        break;
                    case "REPORT_MISSED":
                        center.getMissedExpressDeliveries().forEach(p -> System.out.println(p.getId()));
                        break;
                    case "AUDIT":
                        center.showAuditTrail();
                        break;
                    case "EXIT":
                        return;
                    case "HELP":
                        System.out.println("Commands: PLACE_ORDER, REGISTER_RIDER, UPDATE_RIDER_STATUS, ASSIGN, COMPLETE_DELIVERY, REPORT_RIDER, REPORT_MISSED, AUDIT, EXIT");
                        break;
                    default:
                        System.out.println("Unknown command. Type HELP.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
