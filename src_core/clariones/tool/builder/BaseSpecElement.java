package clariones.tool.builder;

import java.util.ArrayList;
import java.util.List;

public class BaseSpecElement<T extends BaseSpecElement> {
    protected String name;
    protected String title;
    protected String briefComments;
    protected List<String> detailComments;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBriefComments() {
        return briefComments;
    }
    public void setBriefComments(String briefComments) {
        this.briefComments = briefComments;
    }
    public List<String> getDetailComments() {
        if (detailComments == null){
            detailComments = new ArrayList<>();
        }
        return detailComments;
    }
    public void setDetailComments(List<String> detailComments) {
        this.detailComments = detailComments;
    }

    public T addComments(String comments){
        getDetailComments().add(comments);
        return (T) this;
    }
}
