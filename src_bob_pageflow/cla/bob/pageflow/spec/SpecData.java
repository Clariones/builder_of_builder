package cla.bob.pageflow.spec;

import clariones.tool.builder.BaseSpecElement;
import clariones.tool.builder.Utils;

import java.util.ArrayList;
import java.util.List;

public class SpecData extends BaseSpecElement<SpecData> {
    protected SpecData parent;
    protected List<SpecData> children;

    public SpecData getParent() {
        return parent;
    }

    public void setParent(SpecData parent) {
        this.parent = parent;
    }

    public List<SpecData> getChildren() {
        if (children == null){
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<SpecData> children) {
        this.children = children;
    }

    public void addChild(SpecData newSpec) {
        getChildren().add(newSpec);
        newSpec.setParent(this);
    }

    public SpecData findChildByName(String name) {
        if (children == null || children.isEmpty()){
            return null;
        }
        for (SpecData child : children) {
            if (Utils.nameEquals(child.getName(), name)){
                return child;
            }
            SpecData grandChild = child.findChildByName(name);
            if (grandChild != null){
                return grandChild;
            }
        }
        return null;
    }
}
