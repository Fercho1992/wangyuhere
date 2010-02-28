/**
 * Created on Feb 17, 2010, 10:47:35 PM
 * Author: wangyu
 */

package com.jobservice.util;

import com.jobservice.University;
import hw1.Main;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystem {

    public static String SERVER_PATH = "E:\\Study\\ID2212\\homework\\project\\JobService\\web\\";

    public static String readFile(String file) {
        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(SERVER_PATH+file);
            BufferedReader br = new BufferedReader(fr);
            while(br.ready()) {
                sb.append(br.readLine());
                sb.append("\n");
            }
        } catch (Exception ex) {
            Logger.getLogger(University.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sb.toString();
    }

    public static void writeFile(String file, String content) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(SERVER_PATH + file);
            fw.write(content);
            fw.flush();
        } catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void process(String profile, String trans, String record, String info, String cv){
        Main.process(SERVER_PATH + profile, SERVER_PATH + trans, SERVER_PATH + record, SERVER_PATH + info, SERVER_PATH + cv);
    }

    public static Properties getProperties(String file) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(SERVER_PATH+file));
        } catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}
