package com.ztesoft.zsmart.ci.jacoco.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.jacoco.core.data.SocketClientData;
import org.jacoco.core.runtime.RemoteControlReader;


/**
 * jacoco server 所有的jacoco 都连接到此服务上
 * 
 * @author chm
 */
public class JacocoServer {


    /** 日志 */
    private static Logger logger = Logger
            .getLogger(JacocoServer.class);
    
    /** jacoco server port */
    private static final int PORT = 7193;

    /**
     * 启动jacoco server 监听 使用默认 端口
     * 
     * @throws IOException 异常
     * @throws InterruptedException 异常
     */
    public void startMonitor() throws InterruptedException, IOException {
        startMonitor(PORT);
    }

    /**
     * 启动jacoco server 监听
     * 
     * @param port 端口
     * @throws IOException 异常
     * @throws InterruptedException 异常
     */ 
    public void startMonitor(int port) throws IOException, InterruptedException {
        MonitorHeart mh = new MonitorHeart();
        new Thread(mh).start();
        
        ServerSocket server = new ServerSocket(port);
        System.out.println("jacoco monitor port: " + port);
        while (true) {
            try {
                System.out.println("jacoco monitor ....--------");
                Socket socket = server.accept();
                System.out.println("new socket ----------- " + socket);
                new Thread(new ReadJacocoClient(socket)).start();
//                wrapperJacocoClient(socket);
                System.out.println("jacoco client: " + socket);
            }
            catch (Exception e) {
                e.printStackTrace();
                
            }
            
        }

    }

    class ReadJacocoClient implements Runnable {

        private Socket socket;
        public ReadJacocoClient(Socket socket) {
            this.socket = socket;
        }
        
        public void run() {
            // jacoco 被个性化改造了，在tcp握手时，主动发送一个id 信息，此id 信息是agent配置中的一个参数，将来关联流程实例
            System.out.println("wrapperJacocoClient: " + socket);
            RemoteControlReader reader;
            try {
                reader = new RemoteControlReader(socket.getInputStream());
                reader.setNewClient(true);
                SocketClientData clientInfoVisitor = new SocketClientData();
                reader.setClientInfoVisitor(clientInfoVisitor);
                reader.read();
                JacocoClient js = new JacocoClient(socket, clientInfoVisitor.getInfo().getId(), clientInfoVisitor.getInfo().getSessionid());
                JacocoClientMgr.getInstance().addJacocoClient(js);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * 读取jacoco client 数据，主要是id的读取，对jacoco client进行打标
     * 
     * @param socket Socket 对象
     */
    public void wrapperJacocoClient(Socket socket)  {
        // jacoco 被个性化改造了，在tcp握手时，主动发送一个id 信息，此id 信息是agent配置中的一个参数，将来关联流程实例
        System.out.println("wrapperJacocoClient: " + socket);
        RemoteControlReader reader;
        try {
            reader = new RemoteControlReader(socket.getInputStream());
            reader.setNewClient(true);
            SocketClientData clientInfoVisitor = new SocketClientData();
            reader.setClientInfoVisitor(clientInfoVisitor);
            reader.read();
            JacocoClient js = new JacocoClient(socket, clientInfoVisitor.getInfo().getId(), clientInfoVisitor.getInfo().getSessionid());
            JacocoClientMgr.getInstance().addJacocoClient(js);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        
        
        

    }
    
    /**
     * 监控类，用来检测断开的jacococlient
     * @author chm
     *
     */
    class MonitorHeart implements Runnable {

        public void run() {
            while(true) {
                try {
                    Thread.sleep(5000);
                    
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                
                ConcurrentLinkedQueue<JacocoClient> clientList = JacocoClientMgr.getInstance().getClientList();
                logger.info("clientList: " + clientList);
                System.out.println("clientList: " + clientList);
                for (JacocoClient jc : clientList) {
                    try {
                        DataOutputStream dout = new DataOutputStream(jc.getSocket().getOutputStream());
                        
                        //如果此处大量的写入，而客户端一直没有读取，那么在缓冲区被写满以后将会被挂起，直到客户端读取了管道中的数据，或是客户端断开抛出异常
                        //所以此处需要考虑这种极端的情况防止出现了因为一个客户端失败导致所有客户端的心跳被挂起的情况，增加一个守护线程
                        
                        ///** Block identifier for BLOCK_HEART. */
                        //public static final byte BLOCK_HEART = 0x31;
                        
                        dout.writeByte(0x31);
                        dout.writeLong(System.currentTimeMillis());
                        dout.flush();
                    }
                    catch (IOException e) {
                        JacocoClientMgr.getInstance().deleteJacocoClient(jc);
                        e.printStackTrace();
                    }
                }
            }
        }
        
    }
    

}
