package clariones.tool.builder.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

public class Tree<N> {


    public static class Node<N> {
        protected N data;
        protected transient Node<N> parent;
        protected List<Node<N>> children;
        public boolean isLeaf(){
            return children == null || children.isEmpty();
        }
        public boolean isRoot(){
            return parent == null;
        }

        public N getData() {
            return data;
        }
        public void setData(N data) {
            this.data = data;
        }
        public List<Node<N>> getChildren() {
            return children;
        }
        public void setChildren(List<Node<N>> children) {
            this.children = children;
        }
    }
    protected Node<N> root;

    public Node<N> getRoot() {
        return root;
    }

    public void setRoot(Node<N> root) {
        this.root = root;
    }

    public Tree() {
        super();
    }
    public Tree(N rootData) {
        this();
        root = new Node<>();
        root.setData(rootData);
    }

    public Node<N> addLeaf(Node<N> parent, N data){
        if (parent.getChildren() == null){
            parent.setChildren(new LinkedList<>());
        }
        Node<N> newNode = new Node<>();
        newNode.setData(data);
        newNode.parent = parent;
        parent.getChildren().add(newNode);
        return newNode;
    }

    public void remove(Node<N> node){
        if (node.isRoot()){
            root = null;
            return;
        }
        node.parent.getChildren().remove(node);
    }

    public List<N> getByDeepFirstSearch(BiConsumer<Node<N>, String> callback) {
        List<N> result = new ArrayList<>();
        addByDFS(result, root, callback);
        return result;
    }

    public List<N> getByBreadthFirstSearch() {
        List<N> result = new ArrayList<>();
        Queue<Node<N>> queue = new LinkedList<>();
        addByBFS(result, root, queue);
        return result;
    }

    private void addByDFS(List<N> list, Node<N> node, BiConsumer<Node<N>, String> callback) {
        list.add(node.getData());
        if (callback != null){
            callback.accept(node, "picked");
        }
        if (node.isLeaf()){
            if (callback != null){
                callback.accept(node, "is_leaf");
            }
            return;
        }
        for (Node<N> child : node.getChildren()) {
            addByDFS(list, child, callback);
        }
    }

    private void addByBFS(List<N> list, Node<N> node, Queue<Node<N>> queue) {
        list.add(node.getData());
        if (node.isLeaf()){
            return;
        }
        for (Node<N> child : node.getChildren()) {
            queue.add(child);
        }
        Node<N> newNode;
        while((newNode = queue.poll()) != null){
            addByBFS(list, newNode, queue);
        }
    }

//    public static  void main(String[] args) {
//        Tree<Integer> tree =  new Tree<>(1);
//        Node<Integer> n1 = tree.addLeaf(tree.getRoot(), 2);
//        Node<Integer> n2 = tree.addLeaf(n1, 3);
//        Node<Integer> n3 = tree.addLeaf(n1, 4);
//        Node<Integer> n4 = tree.addLeaf(n2, 5);
//        Node<Integer> n5 = tree.addLeaf(tree.getRoot(), 6);
//        Node<Integer> n6 = tree.addLeaf(n5, 7);
//        Node<Integer> n7 = tree.addLeaf(n5, 8);
//
//        List<Integer> list = tree.getByDeepFirstSearch();
//        System.out.println(list);
//        list = tree.getByBreadthFirstSearch();
//        System.out.println(list);
//    }
}
