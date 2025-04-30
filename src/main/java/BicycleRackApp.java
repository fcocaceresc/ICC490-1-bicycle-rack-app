public class BicycleRackApp {
    /**
     * Main method to run the Bicycle Rack app. Creates an instance of BicycleRackService and BicycleRackUI, and calls the menu method to start the app.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        BicycleRackService service = new BicycleRackService();
        BicycleRackUI ui = new BicycleRackUI(service);
        ui.menu();
    }
}
