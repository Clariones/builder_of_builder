package clariones.poc.pathmap;

import java.util.HashMap;
import java.util.Map;

/**
 * 立足点 中的链接点.
 *
 * 有3个重要属性:
 * 1. 类型: begin: 起点, 没有"from";
 * 2. 别名. 每个connector都有自己的别名,这个是必须有的.
 * 3. 上游链接点: 不是begin的点,都必须有一个"来源"
 *
 * 其他一些辅助信息.
 */
public class Connector {
    protected String type;  // begin: 起点
    protected String aliasName;
    protected transient Connector upstream;
    protected String upstreamName;
    protected String id = this.hashCode()+"";

    protected String footHolderName;
    protected String pathName;
    protected String pathType;  // "upstream": path是 upstream.path = me.id; "downstream": path是 upstream.id = me.path
    protected String memberName;

    protected Map<String, Object> extraData = new HashMap<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Connector getUpstream() {
        return upstream;
    }

    public void setUpstream(Connector upstream) {
        this.upstream = upstream;
        this.upstreamName = upstream.id;
    }

    public String getFootHolderName() {
        return footHolderName;
    }

    public void setFootHolderName(String footHolderName) {
        this.footHolderName = footHolderName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathType() {
        return pathType;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
