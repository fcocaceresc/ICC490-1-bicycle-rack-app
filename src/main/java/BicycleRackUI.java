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

    /**
     * Constructor for the BicycleRackUI class.
     * @param service the BicycleRackService instance to be used for the record management
     */
    public BicycleRackUI(BicycleRackService service) {
        this.service = service;
    }

    /**
     * A loop that shows the menu options and executes the selected option until the user chooses to exit.
     */
    public void menu() {
        int option;
        do {
            showOptions();
            option = getOption();
            executeOption(option);
        } while (option != options.size());
    }

    /**
     * Shows the menu options to the user.
     */
    private static void showOptions() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }
    }

    /**
     * A loop that reads the user input and continues infinitely until a valid option is entered.
     * @return the option selected by the user
     */
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

    /**
     * Executes the selected option by calling the corresponding method.
     * @param option the option selected by the user
     */
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

    /**
     * Checks in a bicycle by reading the student ID, name and bicycle description from the user input and calling the checkIn method of the service.
     */
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

    /**
     * First, asks the user if he wants to search the record to check out by record ID or student ID. Then, calls the corresponding method.
     */
    private void checkOutBicycle() {
        System.out.println("[1] Check out by record ID");
        System.out.println("[2] Check out by student ID");
        Integer option = readInteger();
        if (option == null) {
            return;
        }
        switch (option) {
            case 1:
                checkOutByRecordId();
                break;
            case 2:
                checkOutByStudentId();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    /**
     * Checks out a bicycle by ID by reading the record ID from the user input and calling the checkOutByRecordId method of the service.
     */
    private void checkOutByRecordId() {
        System.out.print("Enter the ID of the record to check out: ");
        Integer recordId = readInteger();
        if (recordId == null) {
            return;
        }
        try {
            service.checkOutByRecordId(recordId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Bicycle checked out successfully");
    }

    /**
     * Checks out a bicycle by student ID by reading the student ID from the user input and calling the checkOutByStudentId method of the service.
     */
    private void checkOutByStudentId() {
        System.out.print("Enter the student ID to check out: ");
        String studentId = scanner.nextLine().trim();
        try {
            service.checkOutByStudentId(studentId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Bicycle checked out successfully");
    }

    /**
     * Reads an integer from the user input
     * @return the integer read from the user input or null if the input is not an integer
     */
    private static Integer readInteger() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("The input must be a whole number");
            return null;
        }
    }

    /**
     * Lists all the records by calling the getRecords method of the service and then printing them.
     */
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

    /**
     * First, it asks the user which field he wants to update. Then, it reads the record ID and calls the corresponding method.
     */
    private void updateRecord() {
        System.out.println("[1] Update student ID");
        System.out.println("[2] Update student name");
        System.out.println("[3] Update bicycle description");
        Integer option = readInteger();
        if (option == null) {
            return;
        }
        System.out.print("Enter the ID of the record to update: ");
        Integer recordId = readInteger();
        if (recordId == null) {
            return;
        }
        updateRecordOptions(option, recordId);
    }

    /**
     * Updates a record by calling the corresponding update method of the service based on the selected option.
     * @param option the option selected by the user
     * @param recordId the ID of the record to update
     */
    private void updateRecordOptions(int option, int recordId) {
        switch (option) {
            case 1:
                updateStudentIdRecord(recordId);
                break;
            case 2:
                updateStudentNameRecord(recordId);
                break;
            case 3:
                updateBicycleDescriptionRecord(recordId);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    /**
     * Updates the student ID of a record by reading the new student ID from the user input and calling the updateStudentId method of the service.
     * @param recordId the id of the record to update
     */
    private void updateStudentIdRecord(int recordId) {
        System.out.print("Enter new student ID: ");
        String newStudentId = scanner.nextLine().trim();
        try {
            service.updateStudentId(recordId, newStudentId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the student name of a record by reading the new student name from the user input and calling the updateStudentName method of the service.
     * @param recordId the id of the record to update
     */
    private void updateStudentNameRecord(int recordId) {
        System.out.print("Enter new student name: ");
        String newStudentName = scanner.nextLine().trim();
        try {
            service.updateStudentName(recordId, newStudentName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the bicycle description of a record by reading the new bicycle description from the user input and calling the updateBicycleDescription method of the service.
     * @param recordId the id of the record to update
     */
    private void updateBicycleDescriptionRecord(int recordId) {
        System.out.print("Enter new bicycle description: ");
        String newBicycleDescription = scanner.nextLine().trim();
        try {
            service.updateBicycleDescription(recordId, newBicycleDescription);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
