package com.ztesoft.zsmart.ci.jacoco.test;

import java.io.IOException;

import com.ztesoft.zsmart.ci.jacoco.server.JacocoClientMgr;
import com.ztesoft.zsmart.ci.jacoco.server.JacocoServer;

public class StartServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        
        StartServer ss = new StartServer();
        ss.start(1234567l, 7193);
//        ss.start(Long.valueOf(args[0]), Integer.valueOf(args[1]));
    }
    
    public void start(long instanceId , int port) throws InterruptedException, IOException {
        
//        ReadCoverCode rcc = new ReadCoverCode(instanceId);
//        new Thread(rcc).start();
        
        JacocoServer js = new JacocoServer();
        js.startMonitor(port);
        
    }
    
    
    class ReadCoverCode implements Runnable {
        private long instanceId;
        public ReadCoverCode(long instanceId) {
            this.instanceId = instanceId;
        }
        public void run() {
            while(true) {
                try {
                    Thread.sleep(30000);
//                    JacocoClientProcessor jcp = new JacocoClientProcessor();
//                    List<String> classList = jcp.readClassList(instanceId);
                    
//                    ConcurrentLinkedQueue<JacocoClient> clientList = JacocoClientMgr.getInstance().getClientList();
                    
//                    System.out.println("classList: " + classList);
                    
                    
                    
                }
                catch (Exception e) {
                    JacocoClientMgr.getInstance().deleteJacocoClient(instanceId);
                    e.printStackTrace();
                }
            }
            
            
        }
    }
}
