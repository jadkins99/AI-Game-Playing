package DecentPlayers;

import ProjectThreeEngine.AIGameText;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;
import java.util.ArrayList;

public class Array_As_File {
    /*public static void main(String[] args){
        // the test array
        int[][] list = {
            {1,1,1},
            {2,2,2},
            {3,3,3}
        };

        // function call to write to a new file
        to_file(list, "file_test");

        // function call to read from the file
        int[][] ret = from_file("file_test");
        System.out.println(ret);
    }*/

    // to_file takes in an arraylist and the name of the file to write the array too
    public static void to_file(float[][] to_write, String file_name){

        StringBuilder builder = new StringBuilder();
        

        for(int i = 0; i < to_write.length; i++) {
            for(int o = 0; o < to_write[i].length; o++) {

                builder.append(to_write[i][o]+"");
                if(o < to_write.length - 1) {
                    builder.append(",");
                }
            }
            builder.append("\n");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name + ".txt"));
            writer.write(builder.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // from_file takes the name of the file that the array is stored in
    // and returns an arraylist
    public static float[][] from_file(String file_name, int my_rows, int my_columns){
        float[][] array = new float[my_rows][my_columns];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_name + ".txt"));
            String line = "";
            int row = 0;

            while((line = reader.readLine()) != null)
            {
                String[] cols = line.split(","); 
                int col = 0;
                for(String  c : cols) {
                    array[row][col] = Integer.parseInt(c);
                    col++;
                }
                row++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }
}
