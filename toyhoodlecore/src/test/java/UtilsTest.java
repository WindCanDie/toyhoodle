import com.toy.toyhoodle.core.util.Utils;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljx on 2016/12/22.
 * com.to.toyhoodle.core.utile.Utils Test
 */

public class UtilsTest {
    @Test
    public void sysPropertiesTest() {
        long time = System.currentTimeMillis();
        Map<String, String> map = Utils.getSystemProperties();
        System.out.println(System.currentTimeMillis() - time);
        System.out.print(map);
    }

    @Test
    public void getClassPathTest() {
        System.out.print(Utils.getClassPath());
    }

    @Test
    public void intTets() {
        int a = 1;
        Map<String, Object> aa = new HashMap<>();
        aa.put("a", 1);
        System.out.print(aa.get("a").toString());
        List list;
    }
}
