package root;

/**
 * Process Class
 *
 * @author Shuhao Bai on 10/31/19
 */
public class Process {
    //flag - label if process has entered readyQueue already
    //false : never entered readyQueue
    //true: already entered readyQueue
    public boolean flag = false;

    //process pid
    public int pid = 0;

    //process burst time
    public double pBurstTime = 0d;

    //process running time
    public double pRunningTime = 0d;

    //process has already run time at the very moment
    public double pRanTime = 0d;

    //process remaining burst time
    public double pRemainingBurstTime = 0d;

    //after finishing the interrupt, next new start time
    public double nextNewStartTime = 0d;

    //process start time
    public double pStartTime = 0d;

    //process wait time
    public double pWaitTime = 0d;

    //process complete time
    public double pCompleteTime = 0d;

    //process arrival time (the time that arrives at ready queue)
    public double pArrivalTime = 0d;

    //process previous end-point time
    public double pPreviousEndPointTime = 0d;

    //process turnaround time
    public double pTurnAroundTime = 0d;

    //process mean inter-I/O intervals
    public double pInterval = 0d;

    //process current time
    public double pCurrentTime = 0d;

    //exponentially IOIntervalStartTime
    public double pIOIntervalStartTime = 0d;

    //for SJF predicted remaining burst time
    public double pPredictRemainingBurstTime = 0d;

    //The mean inter-I/O intervals for the jobs are :{30, 35, 40, 45, 50, 55, 60, 65, 70}
    //public double[] IOInterval = {30.0, 35.0, 40.0, 45.0, 50.0, 55.0, 60.0, 65.0, 70.0};

    public Process(int pid, double pBurstTime, double pInterval, double pRemainingBurstTime, double pIOIntervalStartTime) {
        this.pid = pid;
        this.pBurstTime = pBurstTime;
        this.pInterval = pInterval;
        this.pRemainingBurstTime = pRemainingBurstTime;
        this.pIOIntervalStartTime = pIOIntervalStartTime;
    }
}

