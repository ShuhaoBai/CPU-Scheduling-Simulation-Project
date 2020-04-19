package root;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import static root.SJF.*;

/**
 * @author Shuhao Bai on 11/8/19
 */
public class SJFCPU implements Runnable{
    //process start time
    public static double StartTime = 0d;

    //process complete time
    public static double CompleteTime = 0d;

    //System current time
    public static double SystemCurrentTime = StartTime;

    public static double PrevProTimeSpent = 0d;

    public static Queue<Process> finishQueue = new LinkedList<Process>();

    public static double[][] res = new double[10][3];

    //for SJF predicted remaining burst time
    public double predictRemainingBurstTime = 0d;
    //public static double alpha = 0.5;



    @Override
    public void run() {

        // log
        FileWriter fw = null;
        try {
            File f=new File("./SJF_Output_alpha=0.333.log");
            f.createNewFile();
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fw);


        while (!list.isEmpty()) {
            if (readyQueue.size() > 0) {

                Process pro = readyQueue.poll();
                if (pro != null) {

                    pw.println("Process"+pro.pid+"leaving readyQueue at: " + SystemCurrentTime);
                    pw.flush();

                    if (pro != null) {
                        double _pIOIntervalStartTime = 0;
                        _pIOIntervalStartTime = root.RandomGen.exponentiallyRand(pro.pInterval);
                        pro.pIOIntervalStartTime = _pIOIntervalStartTime;
                        pro.pWaitTime += pro.pIOIntervalStartTime;
                        pro.pRemainingBurstTime = pro.pRemainingBurstTime - pro.pIOIntervalStartTime;
                    }

                    if (pro.pRemainingBurstTime <= 0){
                        list.remove(pro);
                        pro.pTurnAroundTime = SystemCurrentTime;
                        res[pro.pid][0] = pro.pTurnAroundTime;
                        res[pro.pid][1] = pro.pRanTime;
                        res[pro.pid][2] = pro.pWaitTime;
                        continue;
                    } else if ( pro.pRemainingBurstTime < pro.pInterval) {
                        pro.pRanTime += pro.pRemainingBurstTime;
                        //pro.pRemainingBurstTime = 0;
                        SystemCurrentTime += pro.pRanTime;
                        list.remove(pro);
                        res[pro.pid][0] = pro.pTurnAroundTime;
                        res[pro.pid][1] = pro.pRanTime;
                        res[pro.pid][2] = pro.pWaitTime;
                    } else {
                        //move process to root.IOChannel to handle interrupt
                        try {
                            Thread.sleep((long) pro.pIOIntervalStartTime + (long) pro.pInterval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //root.IOChannel handled interrupt, and adding 60ms to pWaitTime
                        pro.pRanTime += 60d + pro.pInterval;

                        //Update SystemCurrentTime
                        SystemCurrentTime += pro.pRanTime;

                        pro.pPreviousEndPointTime = SystemCurrentTime - 60 - pro.pInterval;
                        IOQueue.offer(pro);
                        pw.println("Process"+pro.pid+"entering IOQueue at: " + SystemCurrentTime);
                        pw.flush();

                    }
                }

            }
        }
        double totalTurnaroundTime = 0;
        double totalRunningTime = 0;
        double totalWaitingTime = 0;
        for(int i = 0; i < 10; i ++){
            totalTurnaroundTime += res[i][0];
            totalRunningTime += res[i][1];
            totalWaitingTime += res[i][2];
        }
        double CPUUtilization = totalRunningTime/SystemCurrentTime*100;
        double throughput = 10/(SystemCurrentTime/1000/60);
        double averageTurnaroundTime = totalTurnaroundTime/1000/60/10;
        double averageWaitingTime = totalWaitingTime/1000/60/10;

        System.out.println("CPU Utilization: " + CPUUtilization);
        System.out.println("Throughput: " + throughput);
        System.out.println("Average Turnaround Time: " + CPUUtilization);
        System.out.println("Average Waiting Time: " + averageWaitingTime);

        pw.println("CPU utilization: "+CPUUtilization+" %"+"\n"+
                "Throughput: "+throughput+" processes/minute"+"\n"+
                "Average turnaround time: "+averageTurnaroundTime+" minutes/process"+"\n"+
                "Average waiting time: "+averageWaitingTime+" minutes/process");
        pw.flush();

    }
}
