package com.ztesoft.zsmart.ci.jacoco;

import java.util.ArrayList;
import java.util.List;

import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;

public class JacocoDto {
    
    private ExecutionDataStore edata = null;
    private SessionInfoStore sinfo = null;
    private List<String> classList = new ArrayList<String>();
    
    public ExecutionDataStore getEdata() {
        return edata;
    }
    public void setEdata(ExecutionDataStore edata) {
        this.edata = edata;
    }
    public SessionInfoStore getSinfo() {
        return sinfo;
    }
    public void setSinfo(SessionInfoStore sinfo) {
        this.sinfo = sinfo;
    }
    public List<String> getClassList() {
        return classList;
    }
    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

}
