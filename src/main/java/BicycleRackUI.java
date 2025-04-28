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
        System.out.println("Check in bicycle");
    }

    private void checkOutBicycle() {
        System.out.println("Check out bicycle");
    }

    private void listRecords() {
        System.out.println("List records");
    }

    private void updateRecord() {
        System.out.println("Update record");
    }
}
