package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @Test
    void successfulLoginTest() throws IOException, InterruptedException {
        String myString = "@";
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        Runtime.getRuntime().exec(Config.path+"f1-testing\\loginTest\\main.exe", null, new File(Config.path+"f1-testing\\loginTest"));
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
        file.delete();
        assertEquals("PASSED",text);

    }
    @AfterAll
    static void tearDown(){
        File file = new File(
                Config.path+"f1-testing\\loginTest\\output.txt");
        file.delete();
    }
}
