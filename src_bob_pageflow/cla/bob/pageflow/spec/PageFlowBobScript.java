package cla.bob.pageflow.spec;

import clariones.tool.bob.script.BaseType;
import clariones.tool.builder.BaseSpecElement;
import clariones.tool.builder.Utils;

import java.util.*;

public class PageFlowBobScript {
    protected String name;
    protected Map<String, String> configs;
    protected List<SpecData> specDataList;
    protected SpecData curSpec = null;
    protected Object curWork = null;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PageFlowBobScript name(String page_flow) {
        setName(name);
        return this;
    }
    public Map<String, String> getConfigs() {
        if (configs == null){
            configs = new HashMap<>();
        }
        return configs;
    }
    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }

    public List<SpecData> getSpecDataList() {
        if (specDataList == null){
            specDataList = new ArrayList<>();
        }
        return specDataList;
    }

    public void setSpecDataList(List<SpecData> specDataList) {
        this.specDataList = specDataList;
    }

    public PageFlowBobScript has_config(String name) {
        return has_config(name, BaseType.STRING);
    }

    public PageFlowBobScript has_config(String name, BaseType type) {
        getConfigs().put(name, type.getName());
        return this;
    }

    public PageFlowBobScript spec(String name) {
        SpecData existSpec = findSpecDataByName(name);
        if(existSpec == null){
            return has_spec(name);
        }
        curSpec = existSpec;
        return this;
    }

    public PageFlowBobScript has_spec(String name) {
        SpecData existSpec = findSpecDataByName(name);
        if (existSpec != null){
            Utils.error("规格 [%s] 已经被定义");
        }

        SpecData newSpec = new SpecData();
        newSpec.setName(name);
        if (curSpec.getParent() != null){
            curSpec.getParent().addChild(newSpec);
        }else{
            getSpecDataList().add(newSpec);
        }
        curSpec = newSpec;
        return this;
    }

    protected SpecData findSpecDataByName(String name) {
        for (SpecData specData : getSpecDataList()) {
            if (Utils.nameEquals(specData.getName(), name)){
                return specData;
            }
        }
        for (SpecData specData : getSpecDataList()) {
            SpecData tgtSpec = specData.findChildByName(name);
            if (tgtSpec != null){
                return tgtSpec;
            }
        }
        return null;
    }


    public PageFlowBobScript zh_CN(String title) {
        if (curWork instanceof SpecData){
            ((SpecData) curWork).setTitle(title);
        }else{
            throw new RuntimeException("未识别当前工作类型,无法设置标题");
        }
        return this;
    }


    public PageFlowBobScript done() {
        Objects.requireNonNull(curSpec);
        curSpec = curSpec.getParent(); // 空指针异常不处理,自己检查写的对不对
        return this;
    }

    public PageFlowBobScript in_it() {
        SpecData newSpec = new SpecData();
        Objects.requireNonNull(curSpec);
        newSpec.setParent(curSpec);
        curSpec = newSpec;
        return this;
    }

    public PageFlowBobScript can_set(String name) {
        return null;
    }
}
