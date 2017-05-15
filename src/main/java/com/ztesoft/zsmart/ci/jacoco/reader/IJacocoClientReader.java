package com.ztesoft.zsmart.ci.jacoco.reader;

import java.io.IOException;

import com.ztesoft.zsmart.ci.jacoco.JacocoDto;
import com.ztesoft.zsmart.ci.jacoco.server.JacocoClient;

/**
 * 当前读取的只有class 名称，后续做进一步的扩展，有可能需要对覆盖的方法以及方法调用的次序给出结果，当前暂不做要求，有需求时在增加读取接口
 * 
 * @author chm
 */
public interface IJacocoClientReader {

    /**
     * 读取jacococlient的代码覆盖数据，现在只返回类
     * 
     * @param client JacocoClient
     * @param isFirstRead 是否是第一次读取
     * @return 被覆盖的类的集合
     * @throws IOException 异常
     */
    public JacocoDto readClientStoreData(JacocoClient client, boolean isFirstRead) throws IOException;
}
