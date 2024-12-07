package main.game.map;

public class BinaryTree {
	    private Node root;

	    public BinaryTree() {
	        this.root = null;
	    }

	    public void setRoot(Node root) {
	        this.root = root;
	    }

	    public Node getRoot() {
	        return this.root;
	    }

	    public static class Node {
	        private String value;
	        private Node left;
	        private Node right;

	        public Node(String value) {
	            this.value = value;
	            this.left = null;
	            this.right = null;
	        }

	        public String getValue() {
	            return value;
	        }

	        public void setLeft(Node left) {
	            this.left = left;
	        }

	        public void setRight(Node right) {
	            this.right = right;
	        }

	        public Node getLeft() {
	            return left;
	        }

	        public Node getRight() {
	            return right;
	        }
	    }

	    public void depthFirstSearch(Node node) {
	        if (node == null) {
	            return;
	        }

	        System.out.println(node.getValue());

	        depthFirstSearch(node.getLeft());
	        depthFirstSearch(node.getRight());
	    }
}