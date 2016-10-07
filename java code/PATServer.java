/**
 * Created by Kanishth on 9/10/2016.
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PATServer {
    public static void main(String args[]) throws IOException
    {
        PrintWriter out = null;
        ServerSocket listener = null;

        try {
            listener = new ServerSocket(5267);

            while (true)
            {
                Socket socket = listener.accept();
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String pin_code = input.readLine();

                    System.out.println(pin_code);

                    Structure structure = Database.read_lat_lng("select LAT,LONG from indiadb");

                    //Convert ds to string
                    String dataset = convert(structure);
                    dataset = "yolo";

                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.println(dataset);
                }
                catch (IOException e) {
                    System.out.println(e);
                }
                finally {
                    socket.close();
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            listener.close();
        }
    }

    public static String convert(Structure structure)
    {
        String dataset="";
        ArrayList<Double> lt = structure.lat;
        ArrayList<Double> lg = structure.lng;

        for(int i=0; i<lt.size(); i++)
        {
            System.out.println(lt.get(i) + "\t" + lg.get(i) + "\t");
            dataset = lt.get(i) + "x" + lg.get(i) + "y";
        }

        return dataset;
    }

}
