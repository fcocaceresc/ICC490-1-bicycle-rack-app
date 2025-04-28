import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BicycleRackUI {
    private final BicycleRackService service;
    private static final ArrayList<String> options = new ArrayList<>(List.of(
            "Check in bicycle",
            "Check out bicycle",
            "List records",
            "Update record",
            "Exit"
    ));
    private static final Scanner scanner = new Scanner(System.in);

    public BicycleRackUI(BicycleRackService service) {
        this.service = service;
    }

    public void menu() {
        int option;
        do {
            showOptions();
            option = getOption();
            executeOption(option);
        } while (option != options.size());
    }

    private static void showOptions() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }
    }

    private static int getOption() {
        System.out.print("Enter your option: ");
        while (true) {
            String input = scanner.nextLine().trim();
            int option;
            try {
                option = Integer.parseInt(input);
                if (option >= 1 && option <= options.size()) {
                    return option;
                }
            } catch (Exception e) {}
            System.out.print("Enter a whole number between 1 and " + options.size() + ": ");
        }
    }

    private void executeOption(int option) {
        switch (option) {
            case 1:
                checkInBicycle();
                break;
            case 2:
                checkOutBicycle();
                break;
            case 3:
                listRecords();
                break;
            case 4:
                updateRecord();
                break;
        }
    }

    private void checkInBicycle() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine().trim();
        Student student = new Student(studentId, studentName);

        System.out.print("Enter bicycle description: ");
        String bicycleDescription = scanner.nextLine().trim();
        try {
            service.checkIn(student, bicycleDescription);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Bicycle checked in successfully");
    }

    private void checkOutBicycle() {
        System.out.println("Check out bicycle");
    }

    private void listRecords() {
        ArrayList<Record> records = service.getRecords();
        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println();
        for (Record record : records) {
            System.out.println("Record ID: " + record.getId());
            System.out.println("Student ID: " + record.getStudent().getId());
            System.out.println("Student name: " + record.getStudent().getName());
            System.out.println("Bicycle description: " + record.getBicycleDescription());
            System.out.println("Check in: " + record.getCheckIn());
            System.out.println("Check out: " + record.getCheckOut());
            System.out.println();
        }
    }

    private void updateRecord() {
        System.out.println("Update record");
    }
}
