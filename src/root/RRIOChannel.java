package root;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static root.RR.*;

/**
 * @author Shuhao Bai on 11/8/19
 */

public class RRIOChannel implements Runnable {
    @Override
    public void run() {
        FileWriter fw = null;
        try {
            File f=new File("./RR_Output.log");
            f.createNewFile();
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);

        while(!list.isEmpty()){
            if(IOQueue.size() > 0){
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Process pro = IOQueue.poll();

                readyQueue.offer(pro);
                pw.println("pro" + pro.pid + " enter ready queue");
                pw.flush();
            }
        }
    }
}
