package com.ztesoft.zsmart.ci.jacoco.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestMulitThreadOpenFile {
    
    
    public static void main(String[] args) {
        
        TestMulitThreadOpenFile tmto = new TestMulitThreadOpenFile();
            tmto.writeMuiltFile();
    }
    
    public void writeMuiltFile() {
        List<String> strList = new ArrayList<String>();
        strList.add("111111");
        strList.add("22222");
        strList.add("33333");
        strList.add("111111");
        strList.add("22222");
        strList.add("33333");
        
        
        for(String str1 : strList) {
            new Thread(new WriteFile(str1)).start();
        }
    }
    
    /**
     * 打开某个文件,此动作不会等待锁，直接抛出异常java.nio.channels.OverlappingFileLockException
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
    
    class WriteFile implements Runnable {

        private String str ;
        public WriteFile(String str ) {
            this.str = str;
        }
        public void run() {
            try {
                OutputStream outp = openFile(new File("f:/mulit.txt"));
                BufferedOutputStream bops = new BufferedOutputStream(outp);
                String strtemp = this.str + "\n";
                bops.write(strtemp.getBytes());
                bops.flush();
                bops.close();
                outp.close();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
}
