package root;

/**
 * @author Shuhao Bai on 11/6/19
 */
public class RandomGen {

    //uniformly distributed random process execution time [2,4] min
    public static double uniformlyDistributedRand(){
        //for test
        return Math.random()*120.0 + 120.0;

        //for real
        //return Math.random()*120000.0 + 120000.0;
    }

    //exponentially random times between I/O requests
    public static double exponentiallyRand(double o){
        double m = 65536.0;
        double x = (-o) * Math.log((Math.random()* m + 1.0) / m);
        return x;
    }
}
