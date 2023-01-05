package ba.edu.ibu;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @Test
    void successfulLoginTest() throws IOException, InterruptedException {
        CommonMethods.clipboardFix();

        Runtime.getRuntime().exec(Config.path+"f1-testing\\loginTest\\goodLogin.exe", null, new File(Config.path+"f1-testing\\loginTest"));
        File file = new File(
                Config.path+"f1-testing\\loginTest\\output.txt");
        while(!file.exists()){
            Thread.sleep(5000);
            file=new File(
                    Config.path+"f1-testing\\loginTest\\output.txt");
        }

        BufferedReader br
                = new BufferedReader(new FileReader(file));

        String text = br.readLine();
        br.close();
        CommonMethods.removeTempFile();
        assertEquals("PASSED",text);
    }
}
