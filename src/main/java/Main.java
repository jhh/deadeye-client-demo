import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.concurrent.CountDownLatch;
import org.strykeforce.deadeye.Deadeye;
import org.strykeforce.deadeye.UprightRectTargetData;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    startNetworkTables();

    // Use NetworkTables to connect to Deadeye pipeline daemon
    var deadeye = new Deadeye<>("C0", UprightRectTargetData.class);

    // prints UprightRectTargetData.toString() to System.out
    deadeye.setTargetDataListener(System.out::println);

    // print target data for 2 seconds
    deadeye.setEnabled(true);
    Thread.sleep(2000L);
    deadeye.setEnabled(false);
  }

  private static void startNetworkTables() throws InterruptedException {
    var connectedSignal = new CountDownLatch(1);
    var inst = NetworkTableInstance.getDefault();
    inst.addConnectionListener((notification) -> connectedSignal.countDown(), true);
    inst.startClient("192.168.1.11");
    connectedSignal.await();
  }
}
