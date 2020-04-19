package root;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.concurrent.PriorityBlockingQueue;


import static root.Process.*;

/**
 * First Come First Serve
 *
 * @author Shuhao Bai on 10/31/19
 */


public class SJF {
    static class comp implements Comparator<Process> {
        public int compare(Process p1, Process p2)
        {
            if (p1.pPredictRemainingBurstTime < p2.pPredictRemainingBurstTime)
            {
                return -1;
            } else  {
                return 1;
            }
        }
    }

    public static PriorityBlockingQueue<Process> readyQueue = new PriorityBlockingQueue<Process>(10, new comp());

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
            File f=new File("./SJF_Output_alpha=0.333.log");
            f.createNewFile();
            fw = new FileWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println("SJF_Output_alpha=0.333");
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

        Thread _SJFCPU = new Thread(new SJFCPU());
        Thread _SJFIOChannel = new Thread( new SJFIOChannel());
        _SJFCPU.start();
        _SJFIOChannel.start();
    }
}

