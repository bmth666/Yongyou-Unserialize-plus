package tools;

public class Evil_main {
    public static void _main(String[] argv) throws Exception {
        try {
            boolean isLinux = true;
            String osTyp = System.getProperty("os.name");
            if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                isLinux = false;
            }
            String[] cmds = isLinux ? new String[]{"bash", "-c", "gnome-calculator"} : new String[]{"cmd.exe", "/c", "calc"};
            Runtime.getRuntime().exec(cmds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
