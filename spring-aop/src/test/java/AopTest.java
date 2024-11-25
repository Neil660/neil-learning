import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/*
 * @Classname AopTest
 * @Version information V1.0
 * @Date 2024/1/15
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class AopTest {
    @Test
    public void test1() {
        List<Vo> list = new ArrayList<>();
        list.add(new Vo("a"));
        list.add(new Vo("b"));
        Map<String, List<Vo>> map1;
        Map<String, List<Vo>> map2;

        map1 = list.stream().filter(e -> e.equals("1")).collect(Collectors.groupingBy(Vo::getWopCode));
        map2 = list.stream().filter(e -> e.equals("2")).collect(Collectors.groupingBy(Vo::getWopCode));

        map2.forEach((k, v) -> {
            System.out.println(k);
        });

        for (Entry<String, List<Vo>> entry : map1.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
        }

        System.out.println();
    }

    class Vo {
        private String wopCode;

        public Vo(String wopCode) {
            this.wopCode = wopCode;
        }

        public String getWopCode() {
            return wopCode;
        }

        public void setWopCode(String wopCode) {
            this.wopCode = wopCode;
        }
    }
}
