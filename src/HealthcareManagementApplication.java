import controllers.DataController;
import views.MainApplicationWindow;
import javax.swing.SwingUtilities;

/**
 * Main application entry point.
 * @author Utsav Prajapati
 */
public class HealthcareManagementApplication {
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("HEALTHCARE MANAGEMENT SYSTEM");
        System.out.println("Author: Utsav Prajapati");
        System.out.println("Architecture: Layered MVC");
        System.out.println("Pattern: Double-checked Locking Singleton");
        System.out.println("=".repeat(80));
        
        DataController controller = new DataController("data");
        
        SwingUtilities.invokeLater(() -> {
            MainApplicationWindow window = new MainApplicationWindow(controller);
            window.setVisible(true);
        });
    }
}
