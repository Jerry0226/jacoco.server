package com.ztesoft.zsmart.ci.jacoco.jpa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.jacoco.core.data.ExecutionData;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.ExecutionDataWriter;
import org.jacoco.core.data.SessionInfoStore;

import com.ztesoft.zsmart.ci.jacoco.JacocoDto;

public class CIJacocoExecPersistence {
    
    /**
     * 持久化每个用例的覆盖率文件
     * @param file 文件
     * @param jacocodtoList 参数
     * @throws IOException 异常
     */
    public void persisExec(File file, List<JacocoDto> jacocodtoList) throws IOException {
       
        for (JacocoDto jacocodto : jacocodtoList) {
            SessionInfoStore sinfo = jacocodto.getSinfo();
            ExecutionDataStore edata = jacocodto.getEdata();
            
//            showClassId(edata);
            
            //写入jacoco.exec 文件
            final OutputStream output1 =  openFile(file);
            try {
                final ExecutionDataWriter writer1 = new ExecutionDataWriter(output1);
                writer1.visitSessionInfo(sinfo.getInfos().get(0));
                edata.accept(writer1);
                
            } finally {
                output1.close();
            }
        }
        
    }
    
    /**
     * 打开某个文件
     * @param file 文件
     * @return OutputStream 
     * @throws IOException 异常
     */
    private OutputStream openFile(File file) throws IOException {
        final FileOutputStream fileOut = new FileOutputStream(file, true);
        // Avoid concurrent writes from different agents running in parallel:
        fileOut.getChannel().lock();
        return fileOut;
    }
    
    /**
     * 仅仅是为了测试使用
     * @param edata 数据
     */
    private void showClassId(ExecutionDataStore edata) {
        Collection<ExecutionData> list = edata.getContents();
        for (ExecutionData data : list) {
            System.out.println("=========================== classId: " + data.getId());
            System.out.println("=========================== className: " + data.getName());
        }
    }
    
    
}
