package com.ztesoft.zsmart.ci.jacoco;

import java.io.IOException;
import java.util.List;

/***
 * jacoco 处理接口
 * 
 * @author chm
 */
public interface IJacocoClientProcessor {
    /**
     * 读取jacoco 客户端的类覆盖信息
     * @param isFirstRead 是否是第一次读取数据
     * @param id 关联的流程实例id
     * @return 被覆盖的类的集合
     * @throws IOException 异常
     */
    public List<JacocoDto> readClassCover(long id,  boolean isFirstRead) throws IOException;
}
