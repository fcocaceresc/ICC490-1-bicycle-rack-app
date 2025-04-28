public class BicycleRackApp {
    public static void main(String[] args) {
        BicycleRackService service = new BicycleRackService();
        BicycleRackUI ui = new BicycleRackUI(service);
        ui.menu();
    }
}
