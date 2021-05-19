package DecentPlayers;

import ProjectThreeEngine.AIGameText;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;
import java.util.ArrayList;

public class Array_As_File {

    public static void main(String[] args){
        // the test array
        ArrayList list = new ArrayList(); 
        list.add("array value 1");
        list.add(2222222);
        list.add("array value 3");

        // function call to write to a new file
        to_file(list, "file_test");

        // function call to read from the file
        from_file("file_test");
    }

    // to_file takes in an arraylist and the name of the file to write the array too
    public static void to_file(ArrayList to_write, String file_name){
        try{
            FileOutputStream writeData = new FileOutputStream(file_name + ".ser");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
        
            writeStream.writeObject(to_write);
            writeStream.flush();
            writeStream.close();
        
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // from_file takes the name of the file that the array is stored in
    // and returns an arraylist
    public static ArrayList from_file(String file_name){
        try{
            FileInputStream readData = new FileInputStream(file_name + ".ser");
            ObjectInputStream readStream = new ObjectInputStream(readData);
        
            ArrayList array = (ArrayList) readStream.readObject();
            readStream.close();
            System.out.println(array.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }
}
