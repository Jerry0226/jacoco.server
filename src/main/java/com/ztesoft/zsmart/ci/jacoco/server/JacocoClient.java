package com.ztesoft.zsmart.ci.jacoco.server;

import java.net.Socket;

/**
 * 此类的equals 方法判断的是类变量socket的hashcode值
 * 
 * @author chm
 */
public class JacocoClient {

    /**
     * JacocoSocket的标示，关联流程实例id，用来做流程的关联
     */
    private long id;
    
    /**应用名，命名方式沿用jacoco组件中的sessionid*/
    private String sessionid;
    
    /**
     * jacoco的表示，区分实例Id 是节点还是流程实例的Id
     */

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * 关联的jar
     */
    private String coverIncludes;
    
    public String getCoverIncludes() {
        return coverIncludes;
    }

    public void setCoverIncludes(String coverIncludes) {
        this.coverIncludes = coverIncludes;
    }

    public String getCoverExcludes() {
        return coverExcludes;
    }

    public void setCoverExcludes(String coverExcludes) {
        this.coverExcludes = coverExcludes;
    }

    private String coverExcludes;

    /**
     * Jacoco agent 的socket 连接
     */
    private Socket socket;
    

    public String getRemoteIp() {
        return socket.getInetAddress().getHostAddress().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * 构造函数
     * @param socket Socket 
     * @param id 实例id
     */
    public JacocoClient(Socket socket, long id, String sessionid) {
        this.socket = socket;
        this.id = id;
        this.sessionid = sessionid;
    }

    @Override
    public int hashCode() {
        return socket.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj instanceof JacocoClient) {
            JacocoClient objJacoco = (JacocoClient) obj;
            return objJacoco.hashCode() == this.hashCode();
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Id: " + id + " socket: " + socket + " session: " + this.sessionid;
    }

}
