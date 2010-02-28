/**
 * Created on Feb 18, 2010, 12:32:52 AM
 * Author: wangyu
 */
package jobserviceclient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystem {

    public static String readFile(String file) {
        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                sb.append(br.readLine());
                sb.append("\n");
            }
        } catch (Exception ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString();
    }
}
