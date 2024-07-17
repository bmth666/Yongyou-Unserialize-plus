package exp;

import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception{
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("./cc"));
        inputStream.readObject();
    }
}
