package pg;

import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PGController {
    @RequestMapping("/")
    public String index() {
        return "hello!";
    }

    @RequestMapping("/heavyload")
    public HashMap<String,String> intensiveCalc(@RequestParam("time") String time, @RequestParam("load") double load) throws Exception {
        int numCore = Runtime.getRuntime().availableProcessors();
        int numThreadsPerCore = 2;
        // double load = 0.8;
        final long duration = Integer.parseInt(time) * 1000; //millis

        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, duration).start();
        }

        HashMap<String, String> result = new HashMap<String, String>();
        result.put("core", String.valueOf(numCore));
        result.put("load", String.valueOf(load));
        result.put("time", time);

        return result;
    }

    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}