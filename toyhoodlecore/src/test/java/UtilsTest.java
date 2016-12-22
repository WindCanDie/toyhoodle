import com.toy.toyhoodle.core.util.Utils;
import org.junit.Test;

import java.util.Map;

/**
 * Created by ljx on 2016/12/22.
 * com.to.toyhoodle.core.utile.Utils Test
 */

public class UtilsTest {
    @Test
    public void sysProperties() {
        long time = System.currentTimeMillis();
        Map<String, Object> map = Utils.getSystemProperties();
        System.out.println(System.currentTimeMillis() - time);
        System.out.print(map);
    }
}
