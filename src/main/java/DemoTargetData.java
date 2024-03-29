import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import java.io.IOException;
import java.util.Objects;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.strykeforce.deadeye.DeadeyeJsonAdapter;
import org.strykeforce.deadeye.Point;
import org.strykeforce.deadeye.TargetData;
import org.strykeforce.deadeye.UprightRectTargetData;

public class DemoTargetData extends TargetData {

  static final int DATA_LENGTH = 6;

  /**
   * Bounding box top-left corner point.
   */
  @NotNull
  public final Point topLeft;
  /**
   * Bounding box bottom-right corner point.
   */
  @NotNull
  public final Point bottomRight;
  /**
   * Bounding box center.
   */
  @NotNull
  public final Point center;

  public DemoTargetData() {
    this("", 0, false, new Point(0, 0), new Point(0, 0), new Point(0, 0));
  }

  public DemoTargetData(
      @NotNull String id,
      int serial,
      boolean valid,
      @NotNull Point topLeft,
      @NotNull Point bottomRight,
      @NotNull Point center) {
    super(id, serial, valid);
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
    this.center = center;
  }

  /**
   * Returns bounding box area.
   */
  public int area() {
    return width() * height();
  }

  /**
   * Returns width of bounding box.
   */
  public int width() {
    return bottomRight.x - topLeft.x;
  }

  /**
   * Returns height of bounding box.
   */
  public int height() {
    return bottomRight.y - topLeft.y;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public DeadeyeJsonAdapter getJsonAdapter() {
    return new DemoTargetData.JsonAdapterImpl();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    DemoTargetData that = (DemoTargetData) o;
    return topLeft.equals(that.topLeft)
        && bottomRight.equals(that.bottomRight)
        && center.equals(that.center);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), topLeft, bottomRight, center);
  }

  @Override
  public String toString() {
    return "DemoTargetData{"
        + "topLeft="
        + topLeft
        + ", bottomRight="
        + bottomRight
        + ", center="
        + center
        + "} "
        + super.toString();
  }

  private static class JsonAdapterImpl implements DeadeyeJsonAdapter<DemoTargetData> {

    // json d field: bb.tl().x, bb.tl().y, bb.br().x, bb.br().y, center.x, center.y
    private static final JsonReader.Options OPTIONS = JsonReader.Options.of("id", "sn", "v", "d");

    @Override
    public DemoTargetData fromJson(BufferedSource source) throws IOException {
      JsonReader reader = JsonReader.of(source);
      String id = null;
      int serial = -1;
      boolean valid = false;
      int[] data = new int[DATA_LENGTH];
      reader.beginObject();
      while (reader.hasNext()) {
        switch (reader.selectName(OPTIONS)) {
          case 0:
            id = reader.nextString();
            break;
          case 1:
            serial = reader.nextInt();
            break;
          case 2:
            valid = reader.nextBoolean();
            break;
          case 3:
            reader.beginArray();
            for (int i = 0; i < DATA_LENGTH; i++) {
              data[i] = reader.nextInt();
            }
            reader.endArray();
            break;
          case -1:
            reader.skipName();
            reader.skipValue();
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + reader.selectName(OPTIONS));
        }
      }
      reader.endObject();
      Point topLeft = new Point(data[0], data[1]);
      Point bottomRight = new Point(data[2], data[3]);
      Point center = new Point(data[4], data[5]);
      return new DemoTargetData(
          Objects.requireNonNull(id), serial, valid, topLeft, bottomRight, center);
    }

    @Override
    public String toJson(DemoTargetData targetData) throws IOException {
      Buffer buffer = new Buffer();
      JsonWriter writer = JsonWriter.of(buffer);
      writer.beginObject();
      writer.name("id").value(targetData.id);
      writer.name("sn").value(targetData.serial);
      writer.name("v").value(targetData.valid);
      writer.name("d").beginArray();
      writer.value(targetData.topLeft.x).value(targetData.topLeft.y);
      writer.value(targetData.bottomRight.x).value(targetData.bottomRight.y);
      writer.value(targetData.center.x).value(targetData.center.y);
      writer.endArray();
      writer.endObject();
      return buffer.readUtf8();
    }
  }
}
