import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.strykeforce.deadeye.Deadeye;

public class Main {

  private final static Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws InterruptedException {
    var nti = startNetworkTables();

    // Use NetworkTables to connect to Deadeye pipeline daemon
    var deadeye = new Deadeye<>("F0", DemoTargetData.class, nti);

    // prints UprightRectTargetData.toString() to System.out
    deadeye.setTargetDataListener(td -> logger.info("{}", td));

    // print target data for 2 seconds
    deadeye.setEnabled(true);
    Thread.sleep(2000L);
    deadeye.setEnabled(false);
  }

  private static NetworkTableInstance startNetworkTables() throws InterruptedException {
    var connectedSignal = new CountDownLatch(1);
    var inst = NetworkTableInstance.getDefault();
    inst.addConnectionListener((notification) -> connectedSignal.countDown(), true);
    inst.startClient("192.168.1.30");
    connectedSignal.await();
    return inst;
  }
}
