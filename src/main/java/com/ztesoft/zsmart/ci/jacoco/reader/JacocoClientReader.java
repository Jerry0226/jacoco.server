package com.ztesoft.zsmart.ci.jacoco.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.jacoco.core.data.ExecutionData;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;

import com.ztesoft.zsmart.ci.jacoco.JacocoDto;
import com.ztesoft.zsmart.ci.jacoco.server.JacocoClient;

/***
 * jacoco 消息读取
 * @author chm
 *
 */
public class JacocoClientReader implements IJacocoClientReader {

    /**
     * 读取jacococlient的代码覆盖数据，现在只返回类
     * 
     * @param client JacocoClient
     * @param isFirstRead 是否是第一次读取
     * @return JaocoDto
     * @throws IOException 异常
     */
    public JacocoDto readClientStoreData(JacocoClient client, boolean isFirstRead) throws IOException {

        List<String> coverClassList = new ArrayList<String>();

        final ExecutionDataStore edata = new ExecutionDataStore();
        final SessionInfoStore sinfo = new SessionInfoStore();
        final RemoteControlWriter writer = new RemoteControlWriter(client.getSocket().getOutputStream());
        final RemoteControlReader reader = new RemoteControlReader(client.getSocket().getInputStream());
        
        //为了适应jacoco的数据校验，所以此处的数据头保留第一次的writeheader的内容，后续多次读取时不再发送此消息
        reader.setNewClient(isFirstRead);
        
        reader.setSessionInfoVisitor(sinfo);
        reader.setExecutionDataVisitor(edata);
        // 重置数据
        writer.visitDumpCommand(true, true);
        writer.flush();
        reader.read();
        
        
        Collection<ExecutionData> list = edata.getContents();
        for (ExecutionData data : list) {
            coverClassList.add(data.getName());
        }
        
        JacocoDto jacocodto = new JacocoDto();
        jacocodto.setClassList(coverClassList);
        jacocodto.setEdata(edata);
        jacocodto.setSinfo(sinfo);

        return jacocodto;
    }
}
