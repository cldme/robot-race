package robotrace;

import java.util.concurrent.TimeUnit;

public class TimeManager {
    
    long starts;
    
    private TimeManager() {
        reset();
    }

    public static TimeManager start() {
        return new TimeManager();
    }

    public TimeManager reset() {
        starts = System.nanoTime();
        return this;
    }
    
    public long time() {
        long ends = System.nanoTime();
        return ends - starts;
    }
    
    public long time(TimeUnit unit) {
        return unit.convert(time(), TimeUnit.NANOSECONDS);
    }
    
}
