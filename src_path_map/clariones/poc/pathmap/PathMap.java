package clariones.poc.pathmap;


import clariones.tool.builder.utils.Tree;

import java.util.List;

/**
 * Path 的 Map: 带路径的地图.
 *
 * 这个地图不仅有'据点', 还有从不同途径进入(同一个)据点后能够获得的(不同的)接头.
 */
public interface PathMap {
    /** "upstream": path是 upstream.path = me.id; */
    String PATH_UPSTREAM = "upstream";
    /** "downstream": path是 upstream.id = me.path */
    String PATH_DOWNSTREAM = "downstream";

    String CONNECTOR_BEGIN = "begin";
    String CONNECTOR_NORMAL = "common";

    /**
     * 从某个节点开始,准备开始一段 path
     * @param nodeName: 立足点的名字
     * @param beRelated: true: 要求被找的这个节点, 必须是和现有的节点有关联, 找不到关联则返回null; false: 不需要有关联, 找不到就创建一个新的
     * @return 此节点的'别名'. 别名将会在全局唯一, 一般是 type+id 这样的结构.
     */
    String startFrom(String  nodeName,  boolean beRelated);

    /**
     * 从当前指向的节点, 向下一个节点前进.
     * @param targetNodeName 目标节点的名字
     * @param byPath 通过哪条路径
     * @param  pathType : true: 从From到Target是从'多'的一方,到'1'的一方;
     *                  false: 从From到Target是从'1'的一方,到'多'的一方;
     *                  你也可以理解成: true: to Parent; false: to children
     *
     * @return target 的'别名'
     */
    String toNode(String targetNodeName, String byPath, String pathType, String memberName);

    /**
     * 获取所有的立足点
     * @return
     */
    List<FootHolder> getAll();

    /**
     * 从某个 connector 开始,以之为root, 提取其下的相关connector为一个 tree
     * @param rootConnector
     * @return
     */
    Tree<Connector> getTreeFrom(Connector rootConnector);

    /**
     * 根据 Connector的名字获取connector
     * @param beanConnectorName
     * @return
     */
    Connector getConnectorByAliasName(String beanConnectorName);
}
