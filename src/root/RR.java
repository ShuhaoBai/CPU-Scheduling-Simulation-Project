package root;

import org.omg.CORBA.Current;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * SJF
 *
 * @author Shuhao Bai on 10/31/19
 */

public class RR {
    public static BlockingQueue<Process> readyQueue = new LinkedBlockingQueue<>();

    public static BlockingQueue<Process> IOQueue = new LinkedBlockingQueue<>();

    //Communication queue between two threads (root.CPU & root.IOChannel)
    public static BlockingQueue<Process> shareQueue = new LinkedBlockingQueue<>();

    public static LinkedList<Process> list = new LinkedList();
    static LinkedList<Integer> Gantt_chart = new LinkedList<>();

    //I/O intervals
    public static double[] meanInterIOInterval = {30.0, 35.0, 40.0, 45.0, 50.0, 55.0, 60.0, 65.0, 70.0, 75.0};
    public static double IOIntervalStartTime = 0d;

    //store results
    public static double[][] result = new double[10][3];

    static FileWriter fw = null;
    public static void main(String[] args){ //<<<<<<<<<<<<<MAIN FUNCTION<<<<<<<<<<<<<<

        FileWriter fw = null;
        try {
            File f=new File("./RR_Output.log");
            f.createNewFile();
            fw = new FileWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println("RR_Output_log");
        pw.flush();

        for(int i = 0; i < meanInterIOInterval.length; i++){
            //pBurst time is randomly generated from root.RandomGen class
            double pBurst = RandomGen.uniformlyDistributedRand();
            double pIOInterval = meanInterIOInterval[i];
            //IOIntervalStartTime = root.RandomGen.exponentiallyRand(pIOInterval);

            //root.Process(int pid, double pBurstTime, double pInterval, double pRemainingBurstTime)
            Process newP = new Process(i, pBurst, pIOInterval, pBurst, IOIntervalStartTime);

            //After creating new processes, we add process into ready queue
            //We assume all the process arrive at ready queue at the same time, in sequence of P0, P1, P2, P3, P4, P5...P9
            //readyQueue.add(newP);
            pw.println("pro" + newP.pid + " enter in ready queue");
            pw.flush();
            readyQueue.add(newP);
            list.add(newP);

        }

        Thread _RRCPU = new Thread(new RRCPU());
        Thread _RRIOChannel = new Thread( new RRIOChannel());
        _RRCPU.start();
        _RRIOChannel.start();
    }
}

