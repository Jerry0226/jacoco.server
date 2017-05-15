package com.ztesoft.zsmart.ci.jacoco.server;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * jacoco client 端管理工具
 * 
 * @author chm
 */
public final class JacocoClientMgr {

    /**
     * 对象
     */
    private static JacocoClientMgr jacocoClientMgr = new JacocoClientMgr();

    /**
     * 私有化构造方法
     */
    private JacocoClientMgr() {
    }

    /**
     * 单例返回具体的对象
     * 
     * @return JacocoClientMgr 对象
     */
    public static synchronized JacocoClientMgr getInstance() {
        if (null == jacocoClientMgr) {
            jacocoClientMgr = new JacocoClientMgr();
        }
        return jacocoClientMgr;
    }

    /**
     * jacococlient 集合
     */
    private ConcurrentLinkedQueue<JacocoClient> clientList = new ConcurrentLinkedQueue<JacocoClient>();

    /**
     * 增加JacocoClient
     * 
     * @param client JacocoClient
     */
    public void addJacocoClient(JacocoClient client) {
        System.out.println("Add Client: " + client);
        clientList.add(client);
    }

    /**
     * 删除JacocoClient
     * 
     * @param client JacocoClient
     */
    public void deleteJacocoClient(JacocoClient client) {
        clientList.remove(client);
    }

    
    public void deleteJacocoClient(long instanceId) {
        for (JacocoClient client : clientList) {
            if (client.getId() == instanceId) {
                clientList.remove(client);
            }
        }
    }
    
    /**
     * 判断是否存在client
     * @param instanceId
     * @return
     */
    public boolean isContainClient(long instanceId) {
        boolean isContain = false;
        for (JacocoClient client : clientList) {
            if (client.getId() == instanceId) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }
    
    /**
     * 返回ClientList
     * 
     * @return ConcurrentLinkedQueue<JacocoClient>
     */
    public ConcurrentLinkedQueue<JacocoClient> getClientList() {
        return clientList;
    }

}
