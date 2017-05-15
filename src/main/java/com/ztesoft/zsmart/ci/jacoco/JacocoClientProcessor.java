package com.ztesoft.zsmart.ci.jacoco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ztesoft.zsmart.ci.jacoco.reader.IJacocoClientReader;
import com.ztesoft.zsmart.ci.jacoco.reader.JacocoClientReader;
import com.ztesoft.zsmart.ci.jacoco.server.JacocoClient;
import com.ztesoft.zsmart.ci.jacoco.server.JacocoClientMgr;

/**
 * JacocoClientProcessor 处理类，用来根据id 获取具体的被调用类的集合
 * 
 * @author chm
 */
public class JacocoClientProcessor implements IJacocoClientProcessor {

    /**
     * 获取消息时，如果客户端出现异常，需要把此客户端隔离。
       此种客户端有可能时冗余的，如web 中有多个应用启动时的个别启动失败，那么在开始就有，然后就会销毁
     */
    public List<JacocoDto> readClassCover(long id, boolean isFirstRead) throws IOException {
        IJacocoClientReader clientReader = new JacocoClientReader();
//        List<String> classList = new ArrayList<String>();
        List<JacocoDto> jacocoList = new ArrayList<JacocoDto>();
        ConcurrentLinkedQueue<JacocoClient> clientList = JacocoClientMgr.getInstance().getClientList();
        for (JacocoClient jacocoClient : clientList) {
            try {
                if (jacocoClient.getId() == id) {
                    jacocoList.add(clientReader.readClientStoreData(jacocoClient, isFirstRead));
//                    classList.addAll(jacocodto.getClassList());
                    
                    
                    
//                    if (null == filePath) {
//                        System.out.println("job trigger 触发时的清理动作，不用写入exec 的用例文件");
//                    }
//                    else {
//                        //此处是不同应用手机的覆盖率数据，还没有验证写入的文件是否能被解析，看了源码应该可以
//                        File file = new File("");
//                        CIJacocoExecTask cieTask = new CIJacocoExecTask(file, jacocodto);
//                        C3JPAActionThreadPool4Exec.getInstance().execCommand(cieTask);
//                    }
                    
                    
                }
            }
            catch (Exception e) {
                //如果有客户端异常，要删除异常的客户端
                JacocoClientMgr.getInstance().deleteJacocoClient(jacocoClient);
                e.printStackTrace();
            }
            
        }
        return jacocoList;
    }

}
