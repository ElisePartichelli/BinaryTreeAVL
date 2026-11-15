import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class BinarySearchTree<E extends Comparable<E>> {

    private static final class Node<E> {

        public Node<E> left;
        public Node<E> right;
        public E element;

        public Node(E element) {
            left = null;
            right = null;
            this.element = element;
        }

        // Returns the node degree: 0, 1 or 2
        public int degree() {
            int res = 0;
            if (left != null)
                res++;
            if (right != null)
                res++;
            return res;
        }

        public boolean isBalanced() {// returns whether this node is balanced
            return Math.abs(this.balanceFactor()) <= 1;// balanced when the absolute balance factor is at most 1
        }

        public int height() {// height of the node
            int leftHeight;
            int rightHeight;
            if(this.left==null){
                leftHeight=0;
            }else{
                leftHeight=this.left.height();
            }
            if(this.right==null){
                rightHeight=0;
            }else{
                rightHeight=this.right.height();
            }

            if(leftHeight>rightHeight){
                return 1+leftHeight;
            }else{
                return 1+rightHeight;
            }
        }

        public int balanceFactor() {// right subtree height minus left subtree height
            int leftHeight;
            int rightHeight;
            if(this.left==null){
                leftHeight=0;
            }else{
                leftHeight=this.left.height();
            }
            if(this.right==null){
                rightHeight=0;
            }else{
                rightHeight=this.right.height();
            }

            return rightHeight - leftHeight;
        }


        public Node<E> rotateLeft() {// node points to the left child of its right child, and the right child becomes the new subtree root
            Node<E> rightAux = this.right;
            this.right = rightAux.left;
            rightAux.left = this;
            return rightAux;
        }

        public Node<E> rotateRight() {// node points to the right child of its left child, and the left child becomes the new subtree root
            Node<E> leftAux = this.left;
            this.left = leftAux.right;
            leftAux.right = this;
            return leftAux;
        }

        public Node<E> rotateLeftRight() {
            this.left = this.left.rotateLeft();// left subtree is rotated left
            return this.rotateRight();// node is rotated right
        }

        public Node<E> rotateRightLeft() {
            this.right = this.right.rotateRight();// right subtree is rotated right
            return this.rotateLeft();// node is rotated left
        }


        public Node<E> balance() {// post-order traversal
            if (this.left != null && !this.left.isNodeAndChildrenBalanced()) {// balance the left child if it exists and is unbalanced
                this.left = this.left.balance();
            }
            if (this.right != null && !this.right.isNodeAndChildrenBalanced()) {// balance the right child if it exists and is unbalanced
                this.right = this.right.balance();
            }

            // balance the current node
            if (this.balanceFactor() < -1) {// left subtree is taller
                
                if (this.left.balanceFactor() >= 1) {// left child is right-heavy: double left-right rotation
                    return this.rotateLeftRight();
                }
                return this.rotateRight();// otherwise, single right rotation

            } else if (this.balanceFactor() > 1) {// right subtree is taller
                
                if (this.right.balanceFactor() <= -1) {// right child is left-heavy: double right-left rotation
                    return this.rotateRightLeft();
                }
                return this.rotateLeft();// otherwise, single left rotation
            }
            return this;
        }


        public boolean isNodeAndChildrenBalanced() {// recursively checks whether this node and all its descendants are balanced
            if(this.isBalanced() &&(this.left == null || this.left.isNodeAndChildrenBalanced()) && (this.right == null || this.right.isNodeAndChildrenBalanced())){
                return true;
        }else{
            return false;
        }
        }

        public  String toString(){
            return "" + element;
        }
    }

    // Attributes
    private int count; // number of nodes
    private Node<E> root; // reference to the root node

    public BinarySearchTree() {
        clear();
    }

    public void clear() {
        count = 0;
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    public E getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root.element;
    }

    public void add(E element) {
        Node<E> aux = new Node<E>(element);
        if (root == null) { 
            root = aux;
            count++;
            return;
        }
        boolean inserted = false;
        Node<E> current = root;
        while (!inserted) {
            if (element.compareTo(current.element) < 0) {
                if (current.left == null) {
                    current.left = aux;
                    inserted = true;
                } else
                    current = current.left;
            } else {
                if (current.right == null) {
                    current.right = aux;
                    inserted = true;
                } else
                    current = current.right;
            }
        }
        count++;
    }

    private Node<E> searchNode(E element) {
        Node<E> current = root;
        while (current != null) {
            if (current.element.equals(element))
                return current;
            if (element.compareTo(current.element) < 0)
                current = current.left;
            else
                current = current.right;
        }
        return null;
    }

    public boolean contains(E element) {
        if (searchNode(element) != null)
            return true;
        return false;
    }


    public boolean remove(E value) {
        Node<E> parent = null, current = root;
        boolean hasLeft = false;
        if (root == null)
            return false;

        while (current != null) {
            if (current.element.equals(value)) {
                break;
            }
            parent = current;
            if (value.compareTo(current.element) < 0) {
                hasLeft = true;
                current = current.left;
            } else {
                hasLeft = false;
                current = current.right;
            }
        }
        if (current == null) 
            return false;

        if (parent == null) {
            root = remove(current);
        } else if (hasLeft) {
            parent.left = remove(current);
        } else {
            parent.right = remove(current);
        }
        count--;
        return true;
    }

    private Node<E> remove(Node<E> node) {

        if (node != null) {
            if (node.left == null && node.right == null) {
                return null;
            }

            if (node.left != null && node.right != null) {
                Node<E> inOrderSuccessor = extractInOrderSuccessor(node);
                node.element = inOrderSuccessor.element;
            } else if (node.left != null) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    private Node<E> extractInOrderSuccessor(Node<E> node) {
        Node<E> parent = node;
        node = node.right;
        boolean rightChild = node.left == null;

        while (node.left != null) {
            parent = node;
            node = node.left;
        }

        if (rightChild) {
            parent.right = node.right;
        } else {
            parent.left = node.right;
        }
        node.right = null;
        return node;
    }


    public boolean isBalancedTree(){// true if the whole tree is balanced
        return isBalancedNode(root);// recursive check starting at the root
    }

    private boolean isBalancedNode(Node<E> node) {// true if the node and its subtrees are balanced
        if (node == null) return true;
        int balance = node.balanceFactor();
        if (balance < -1 || balance > 1) return false;// check the node itself
        if (node.left != null) {
            if (!isBalancedNode(node.left)) return false;// check left
        }
        if (node.right != null) {
            if (!isBalancedNode(node.right)) return false;// check right
        }
        return true;
    }

     public void applyBalancing() {
         while(this.root != null && !this.root.isNodeAndChildrenBalanced()) {// keep balancing until the tree is balanced
              this.root = root.balance();// start at the root
        }
     }


    public List<E> inOrder() {
        List<E> res = new LinkedList<>();
        inOrderAux(root, res);
        return res;
    }

    private void inOrderAux(Node<E> n, List<E> res) {
        if (n != null) {
            inOrderAux(n.left, res); // Visit the left subtree
            res.add(n.element); // Visit the node
            inOrderAux(n.right, res); // Visit the right subtree
        }
    }

    public List<E> breadth() {
        List<E> result = new ArrayList<>();
        List<Node<E>> queue = new LinkedList<>();
        if (this.root != null)
            queue.add(this.root);
        while (!queue.isEmpty()) {
            Node<E> current = queue.remove(0);
            result.add(current.element);
            if (current.left != null)
                queue.add(current.left);
            if (current.right != null)
                queue.add(current.right);
        }
        return result;
    }

    private void generateDOTConnections(StringBuilder sb, Node<E> node) {
        if (node == null) {
            return;
        }

        generateDOTConnections(sb, node.left);
        // "nodeA":left -> "nodeB" [color="0.650 0.700 0.700"]
        if (node.left != null) {
            sb.append("\"node" + node.element + "\":left -> \"node" + node.left.element + "\" \n");
        }

        generateDOTConnections(sb, node.right);
        // "nodeA":right -> "nodeB";
        if (node.right != null) {
            sb.append("\"node" + node.element + "\":right -> \"node" + node.right.element + "\" \n");
        }
    }

    private void generateDOTNodes(StringBuilder sb, Node<E> node) {
        if (node == null) {
            return;
        }
        generateDOTNodes(sb, node.left);
        // node10[label = "<left> | 10 | <right> "];
        sb.append("node" + node.element + "[label = \"<left> | " + node.element + " | <right> \"]\n");
        generateDOTNodes(sb, node.right);
    }

    public void generateDOTConnections(StringBuilder sb) {
        generateDOTConnections(sb, root);
    }

    public void generateDOTNodes(StringBuilder sb) {
        generateDOTNodes(sb, root);
    }

    // Generates output in DOT format
    // The output can be visualized with Graphviz
    // Online Graphviz viewers can be found at
    // http://www.webgraphviz.com/
    // http://viz-js.com/
    // https://dreampuf.github.io/GraphvizOnline
    public String toDOT() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph g { \nnode [shape = record,height=.1];\n");
        generateDOTNodes(sb);
        generateDOTConnections(sb);
        sb.append("}\n");
        return sb.toString();
    }
}
