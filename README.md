# CPU-Scheduling-Simulation-Project

Project Description:
Perform a simulation of CPU scheduling algo: FCFS(First Come First Serve), SJF(Shortest Job First), RR(Round Robin).

Simulation Requirement:
1. Create 10 jobs of random execution time with lengths uniformly distributed between 2 and 4 minutes.
2. For each job, the times between I/O requests (i.e, CPU bursts) are distributed exponentially. Themean inter-I/O intervals for the jobs are respectively 30ms, 35ms, 40ms, 45ms, 50ms, 55ms, 60ms, 65ms, 70ms, and 75ms.
3. Each time an I/O is needed it takes precisely 60 ms.
4. A job, once it enters the system, can be either in the Ready queue, or I/O Waiting queue, or it is being executed by the CPU.

Testing rules:  
a). Different simulation runs are to be performed with the First-Come-First-Servedand Shortest-Job-First(SJF) algorithms. 
b). When using SJF, experiment with exponential averaging. Use the alpha values of 1 (no prediction), 1/2, and 1/3, and log the effect on the results.
