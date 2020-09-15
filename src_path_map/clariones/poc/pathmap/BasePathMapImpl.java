package clariones.poc.pathmap;



import clariones.tool.builder.utils.BaseBuilderClass;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class BasePathMapImpl extends BaseBuilderClass implements PathMap {

    protected AtomicInteger connectorCount = new AtomicInteger(1);
    protected FootHolder currentFootHolder = null;
    protected Connector currentConnector = null;

    protected FootHolder firstFootHolder = null;
    protected Connector firstConnector = null;
    protected String startNodeName = null;

    public String nextAlias(){
        return "T" + connectorCount.getAndIncrement();
    }

    @Override
    public String startFrom(String nodeName, boolean beRelated, String declaredPostion) {
        debug("start from %s(%s)", nodeName,declaredPostion);
        boolean isEmpty = this.getAll().size() == 0;
        FootHolder startNode = findFootHolderByName(nodeName);
        if (startNode == null && beRelated){
            // 如果要求是有关联的点,但是找不到,那么就返回null
            return null;
        }
        if (startNode != null){
            if (footHolderHasNoAmbiguity(startNode)){
                currentFootHolder = startNode;
                currentConnector = getFootHolderOnlyOneConnector(startNode);
                return currentConnector.getAliasName();
            }
            // 如果是查找目标的话, 那么 currentFootHolder 就应该是起点, currentConnector就应该是第一条connector
            if (nodeName.equals(startNodeName)){
                currentFootHolder = firstFootHolder;
                currentConnector = firstConnector;
                return currentConnector.getAliasName();
            }
            error("节点%s不能用名字唯一确定,需要更靠近根源的描述(%s)", nodeName, declaredPostion);
        }
        // 现在 startNode 一定为null, 并且 beRelated 是force
        // 那么, 我们就应该新建一个 footHolder, 并给与其别名
        startNode = addNewFootHolder(nodeName);

        Connector beginConnector = addBeginConnector(startNode);
        debug("节点%s是一个起点,别名:%s",nodeName, beginConnector.getAliasName());
        currentFootHolder = startNode;
        currentConnector = beginConnector;
        if (isEmpty) {
            firstFootHolder = currentFootHolder;
            firstConnector = currentConnector;
            startNodeName = nodeName;
        }
        return beginConnector.getAliasName();
    }

    @Override
    public String toNode(String targetNodeName, String byPath, String pathType, String memberName){
        if (currentFootHolder == null || currentConnector == null){
            error("必须先调用 startFrom(xxx)");
        }
        debug("to %s by %s path [%s]", targetNodeName, pathType,byPath );
        Connector tgtConnector = getConnectorByPath(currentConnector, byPath, pathType, targetNodeName);
        if (tgtConnector != null) {
            // 路径已存在
            debug("%s.%s--%s(%s)->%s.%s已存在",
                    currentFootHolder.getName(), currentConnector.getAliasName(),
                    byPath, pathType,
                    targetNodeName, tgtConnector.getAliasName());
            currentFootHolder = findFootHolderByName(targetNodeName);
            currentConnector = tgtConnector;
            return tgtConnector.getAliasName();
        }

        // 如果路径不存在, 那么创建一个新的
        currentFootHolder = findFootHolderByName(targetNodeName);
        if (currentFootHolder == null){
            currentFootHolder = addNewFootHolder(targetNodeName);
        }
        tgtConnector = addNewConnector(currentFootHolder, currentConnector, byPath, pathType);
        tgtConnector.setMemberName(memberName);
        currentConnector = tgtConnector;
        return currentConnector.getAliasName();
    }





    /** 给一个立足点添加一个 'begin' 类型的 connector, 并赋予别名 */
    protected abstract Connector addBeginConnector(FootHolder startNode);

    /** 添加一个空的 立足点, 这时不要给它任何属性 */
    protected abstract FootHolder addNewFootHolder(String nodeName);

    /** 获取节点的唯一 别名 */
    protected abstract Connector getFootHolderOnlyOneConnector(FootHolder startNode);

    /**  判断一个立足点是不是只有一个'入'链接点,甚至是'起点',也就是没有'入'链接. */
    protected abstract boolean footHolderHasNoAmbiguity(FootHolder startNode);

    /** 根据名字找到一个立足点 */
    protected abstract FootHolder findFootHolderByName(String nodeName);

    /** 从 currentConnector 出发, 沿 byPath+pathType, 到达 targetNodeName 类型的立足点时, 对应的 connector. 不负责自动创建 */
    protected abstract Connector getConnectorByPath(Connector currentConnector, String byPath, String pathType, String targetNodeName);

    /** 在 footHolder 中,创建一个 从 fromConnector, 通过 byPath+pathType 来的新Connector, 并赋予别名 */
    protected abstract Connector addNewConnector(FootHolder footHolder, Connector fromConnector, String byPath, String pathType);


}
