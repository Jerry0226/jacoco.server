package com.ztesoft.zsmart.ci.jacoco.jpa;

import java.io.File;
import java.io.IOException;
import java.util.List;


import com.ztesoft.zsmart.ci.jacoco.JacocoDto;

public class CIJacocoExecTask implements Runnable {

    private File file;
    /**
     * 因为同时可能有多个应用，那么将会返回多个JacocoDto对象，此时
     */
    private List<JacocoDto> jacocodtoList ;
    
    public CIJacocoExecTask(File file, List<JacocoDto> jacocodtoList) {
        this.file = file;
        this.jacocodtoList = jacocodtoList;
    }
    
    public void run() {
        CIJacocoExecPersistence cie = new CIJacocoExecPersistence();
        try {
            cie.persisExec(file, jacocodtoList);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
