package exp;

import nc.vo.framework.rsa.Encode;

public class DbPasswdDecode {
    public static void main(String[] args) {
        Encode en = new Encode();
        System.out.println(en.decode("fhkjjjimdphmoecm"));
    }
}
