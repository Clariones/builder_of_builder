package clariones.poc.pathmap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 立足点. 一个名字对应一个立足点.
 *
 * 在同一个立足点里,有多个 链接点. 只要不是同一个链接来源的, 都是不同的链接点.
 */
public class FootHolder {
    protected String name;
    protected Map<String, Connector> connectors = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(Map<String, Connector> connectors) {
        this.connectors = connectors;
    }

    public void addConnector(Connector connector) {
        connectors.put(connector.getAliasName(), connector);
    }

    public boolean isStartNode() {
        if (connectors.size() != 1){
            return false;
        }
        Connector connector = connectors.values().iterator().next();
        return connector.getType().equals(PathMap.CONNECTOR_BEGIN);
    }
}
