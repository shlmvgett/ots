package ru.otus.gc;

import com.sun.management.GarbageCollectionNotificationInfo;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

/*
О формате логов
http://openjdk.java.net/jeps/158


-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/

// Наименование Как включить
//    Serial Collector -XX:+UseSerialGC
//    Parallel Collector -XX:+UseParallelGC
//    CMS -XX:+UseConcMarkSweepGC
//    G1 -XX:+UseG1GC
//    ZGC -XX:+UnlockExperimentalVMOptions -XX:+UseZGC

public class GcDemo {

  public static void main(String... args) throws Exception {
    System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
    switchOnMonitoring();

    int size = 1 * 10_000;
    int loopCounter = 100_000;
    // int loopCounter = 100000;
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName name = new ObjectName("ru.otus:type=Benchmark");

    Benchmark mbean = new Benchmark(loopCounter);
    mbs.registerMBean(mbean, name);
    mbean.setSize(size);
    mbean.run();
  }

  private static void switchOnMonitoring() {
    List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
    Map<String, Long> gcData = new HashMap<>();
    for (GarbageCollectorMXBean gcbean : gcbeans) {
      System.out.println("GC name:" + gcbean.getName());
      NotificationEmitter emitter = (NotificationEmitter) gcbean;
      NotificationListener listener =
          (notification, handback) -> {
            if (notification
                .getType()
                .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
              GarbageCollectionNotificationInfo info =
                  GarbageCollectionNotificationInfo.from(
                      (CompositeData) notification.getUserData());
              String gcName = info.getGcName();
              String gcAction = info.getGcAction();
              String gcCause = info.getGcCause();

              long startTime = info.getGcInfo().getStartTime();
              long duration = info.getGcInfo().getDuration();

              gcData.put(gcName, gcData.getOrDefault(gcName, 0L) + duration);

              System.out.println(
                  "start:" + startTime
                      + " Name:" + gcName
                      + ", action:" + gcAction
                      + ", gcCause:" + gcCause + "(" + duration + " ms)"
                      + ", total: " + gcData.toString());
            }
          };
      emitter.addNotificationListener(listener, null, null);
    }
  }
}
