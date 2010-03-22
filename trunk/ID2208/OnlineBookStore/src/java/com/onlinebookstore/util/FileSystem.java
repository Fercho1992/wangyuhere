/**
 * Created on Feb 17, 2010, 10:47:35 PM
 * Author: wangyu
 */

package com.onlinebookstore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystem {

    public static String SERVER_PATH = "E:\\Documents\\NetBeansProjects\\OnlineBookStore\\";

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
            fr.close();
        } catch (Exception ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sb.toString();
    }

    public static void deleteFile(String file) {
        File f = new File(SERVER_PATH+file);
        System.out.println("Result:" + f.delete());
        System.out.println("delete file "+file);
    }

    public static void writeFile(String file, String content) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(SERVER_PATH + file);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
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
