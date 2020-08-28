package clariones.poc.pathmap;


import clariones.tool.builder.utils.Tree;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryPathMapImpl extends BasePathMapImpl{
    /*
    所有的立足点. 这个算是快查表, 真正的路由信息保存在 路径 中
     */
    protected Map<String, FootHolder> feetHolder = new LinkedHashMap<>();

    @Override
    protected Connector addBeginConnector(FootHolder node) {
        Connector beginConnector = new Connector();
        beginConnector.setType(CONNECTOR_BEGIN);
        beginConnector.setAliasName(nextAlias());
        beginConnector.setFootHolderName(node.getName());
        node.addConnector(beginConnector);
        return beginConnector;
    }

    @Override
    protected FootHolder addNewFootHolder(String nodeName) {
        FootHolder node = new FootHolder();
        node.setName(nodeName);
        feetHolder.put(nodeName, node);
        return node;
    }

    @Override
    protected Connector getFootHolderOnlyOneConnector(FootHolder node) {
        Map<String, Connector> cs = node.getConnectors();
        if (cs.size() != 1){
            error("%s不是只有一个connector", node.getName());
        }
        return cs.values().iterator().next();
    }

    @Override
    protected boolean footHolderHasNoAmbiguity(FootHolder node) {
        Map<String, Connector> cs = node.getConnectors();
        return cs.size() == 1;
    }

    @Override
    protected FootHolder findFootHolderByName(String nodeName) {
        return feetHolder.get(nodeName);
    }

    @Override
    protected Connector getConnectorByPath(Connector fromConnector, String byPath, String pathType, String targetNodeName) {
        FootHolder tgtFootHolder = findFootHolderByName(targetNodeName);
        if (tgtFootHolder == null) {
            return null;  // 目标立足点不存在
        }
        Map<String, Connector> cs = tgtFootHolder.getConnectors();
        for (Connector connector : cs.values()) {
            if (connector.getUpstream() == null){
                continue;
            }
            if (!connector.getUpstream().getAliasName().equals(fromConnector.getAliasName())){
                debug(" checking %s.%s, wanted %s.%s",
                        connector.getFootHolderName(), connector.getUpstream().getAliasName(),
                        fromConnector.getFootHolderName(), fromConnector.getAliasName());
                continue;
            }
            if (byPath.equals(connector.getPathName()) && pathType.equals(connector.getPathType())){
                return connector;
            }
            debug(" found %s.%s, wanted %s.%s",
                    connector.getFootHolderName(), connector.getUpstream().getAliasName(),
                    fromConnector.getFootHolderName(), fromConnector.getAliasName());
            debug(" looking through %s:%s, wanted %s:%s",
                    connector.getPathType(), connector.getPathName(),
                    fromConnector.getPathType(), fromConnector.getPathName());
        }
        return null;
    }

    @Override
    protected Connector addNewConnector(FootHolder footHolder, Connector fromConnector, String byPath, String pathType) {
        Connector newConnector = new Connector();
        newConnector.setType(CONNECTOR_NORMAL);
        newConnector.setAliasName(nextAlias());
        newConnector.setUpstream(fromConnector);
        footHolder.addConnector(newConnector);
        newConnector.setFootHolderName(footHolder.getName());
        newConnector.setPathName(byPath);
        newConnector.setPathType(pathType);
        debug("new connector %s.%s from %s by %s path [%s]",
                newConnector.getFootHolderName(), newConnector.getAliasName(),
                fromConnector.getAliasName(),
                pathType, byPath);
        return newConnector;
    }

    @Override
    public List<FootHolder> getAll() {
        return new ArrayList<>(feetHolder.values());
    }

    @Override
    public Tree<Connector> getTreeFrom(Connector rootConnector) {
        Tree<Connector> tree = new Tree<>(rootConnector);
        addToTree(tree, tree.getRoot(), rootConnector);
        return tree;
    }

    @Override
    public Connector getConnectorByAliasName(String aliasName) {
        return feetHolder.values().stream()
                .flatMap(it->it.getConnectors().values().stream())
                .filter(it->it.getAliasName().equals(aliasName))
                .findFirst().orElse(null);
    }

    private void addToTree(Tree<Connector> tree, Tree.Node<Connector> parentNode, Connector connector) {
        List<Connector> children = findChildrenConnector(connector);
        if (children == null || children.isEmpty()) {
            return;
        }
        for (Connector child : children) {
            Tree.Node<Connector> newNode = tree.addLeaf(parentNode, child);
            addToTree(tree, newNode, child);
        }
    }

    private List<Connector> findChildrenConnector(Connector connector) {
        return feetHolder.values().stream().flatMap(fh->fh.getConnectors().values().stream())
                .filter(c->c.getUpstream() != null && c.getUpstream().getAliasName().equals(connector.getAliasName()))
                .collect(Collectors.toList());
    }
}
