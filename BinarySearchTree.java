import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//André Menezes Bins 21200695-1
//Marcos do Nascimento Ferreira 21200886-6
//Yanny Elise Partichelli 21200661-3

public class BinarySearchTree<E extends Comparable<E>> {

    private static final class Node<E> {

        public Node<E> parent;
        public Node<E> left;
        public Node<E> right;
        public E element;

        public Node(E element) {
            parent = null;
            left = null;
            right = null;
            this.element = element;
        }

        // Retorna o grau do nó: 0, 1 ou 2
        public int degree() {
            int res = 0;
            if (left != null)
                res++;
            if (right != null)
                res++;
            return res;
        }

        public boolean isBalanced() {//retorna se o nó está balanceado
            return Math.abs(this.coeficient()) <= 1;//se o módulo do coeficiente for menor ou igual a um esta balanceado
        }

        public int height() {//altura do nó
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

        public int coeficient() {//diferença da alturas da esq e dir
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


        public Node<E> RotateLeft() {//nó passa a apontar para o filho esquerdo de seu filho da direita, e o filho da direita passa a apontar para o nó
            Node<E> rightAux = this.right;
            this.right = rightAux.left;
            rightAux.left = this;
            return rightAux;
        }

        public Node<E> RotateRight() {//nó passa a apontar para o filho direito de seu filho da esquerda, e o filho da esquerda passa a apontar para o nó
            Node<E> leftAux = this.left;
            this.left = leftAux.right;
            leftAux.right = this;
            return leftAux;
        }

        public Node<E> RotateLeftRight() {
            this.left = this.left.RotateLeft();//no aponta para a subarvore esquerda rotacionada para esq
            return this.RotateRight();//no é rotacionado p dir
        }

        public Node<E> RotateRightLeft() {
            this.right = this.right.RotateRight();//no aponta para a subarvore direita rotacionada p dir
            return this.RotateLeft();//nó é rotacionado p esq
        }


        public Node balance() {//percorre em "pos ordem"
            if (this.left != null && !this.left.isNodeAndChildrenBalanced()) {//se tiver filho na esquerda faz e o mesmo nao estiver balanceado faz o balanceamento
                this.left = this.left.balance();
            }
            if (this.right != null && !this.right.isNodeAndChildrenBalanced()) {//se tiver filho na direita e o mesmo nao estiver balanceado faz o balanceamento
                this.right = this.right.balance();
            }

            //faz o balanceamento do no
            if (this.coeficient() < -1) {//se a subarvore da esquerda tiver maior entra no if
                
                if (this.left.coeficient() >= 1) {//se a subarvore da direita do filho da esquerda for maior faz a rotação dupla esquerda-direita
                    return this.RotateLeftRight();
                }
                return this.RotateRight();//se nao faz rotação simples p direita

            } else if (this.coeficient() > 1) {//senao, se a subarvore da direita tiver maior entra no if
                
                if (this.right.coeficient() <= -1) {//se a subarvore da esquerda do filho da direita for maior faz a rotação dupla direita-esquerda
                    return this.RotateRightLeft();
                }
                return this.RotateLeft();//se nao faz rotacao simples p esquerda
            }
            return this;
        }


        public boolean isNodeAndChildrenBalanced() {//recursao p ver se nodo e seus filhos estao balanceados
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

    // Atributos
    private int count; // contagem do número de nodos
    private Node<E> root; // referência para o nodo raiz

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
        boolean inseriu = false;
        Node<E> atual = root;
        while (!inseriu) {
            if (element.compareTo(atual.element) < 0) {
                if (atual.left == null) {
                    atual.left = aux;
                    aux.parent = atual;
                    inseriu = true;
                } else
                    atual = atual.left;
            } else {
                if (atual.right == null) {
                    atual.right = aux;
                    aux.parent = atual;
                    inseriu = true;
                } else
                    atual = atual.right;
            }
        }
        count++;
    }

    private Node<E> searchNode(E element) {
        Node<E> atual = root;
        while (atual != null) {
            if (atual.element.equals(element))
                return atual;
            if (element.compareTo(atual.element) < 0)
                atual = atual.left;
            else
                atual = atual.right;
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
            return true;

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
            remove(current);
            return true;
        }
        if (hasLeft) {
            parent.left = remove(current);
        } else {
            parent.right = remove(current);
        }
        return true;
    }

    private Node<E> remove(Node<E> node) {

        if (node != null) {
            if (node.left == null && node.right == null) {
                return null;
            }

            if (node.left != null && node.right != null) {
                Node<E> inOrderSuccessor = removeDuplicate(node);
                node.element = inOrderSuccessor.element;
            } else if (node.left != null) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    private Node<E> removeDuplicate(Node<E> node) {
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


    public boolean isBalancedTree(){//true se a arvore estiver balanceada
        return isBalancedNode(root);//chama com a raiz o isBalanced recursivo
    }

    public Boolean isBalancedNode(Node<E> node) {//true se o node esta balanceado
        if (node == null) return true;
        int balance = node.coeficient();
        if (balance < -1 || balance > 1) return false;//verifica o proprio node
        if (node.left != null) {
            if (!isBalancedNode(node.left)) return false;//verifica esquerda
        }
        if (node.left != null) {
            if (!isBalancedNode(node.left)) return false;//verifica direita
        }
        return true;
    }

     public void ApplyBalancing() {
         while(!this.root.isNodeAndChildrenBalanced()) {//aplica o balanceamento se nao estiver balanceada
              this.root = root.balance();//chama na raiz 
        }
     }


    public List<E> positionsCentral() {
        List<E> res = new LinkedList<>();
        positionsCentralAux(root, res);
        return res;
    }

    private void positionsCentralAux(Node<E> n, List<E> res) {
        if (n != null) {
            positionsCentralAux(n.left, res); // Visita a subárvore da esquerda
            res.add(n.element); // Visita o nodo
            positionsCentralAux(n.right, res); // Visita a subárvore da direita
        }
    }

    public List<E> breadth() {
        List<E> result = new ArrayList<>();
        List<Node<E>> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            Node<E> atual = queue.remove(0);
            result.add(atual.element);
            if (atual.left != null)
                queue.add(atual.left);
            if (atual.right != null)
                queue.add(atual.right);
        }
        return result;
    }

    private void geraConexoesDOT(StringBuilder sb, Node<E> nodo) {
        if (nodo == null) {
            return;
        }

        geraConexoesDOT(sb, nodo.left);
        // "nodeA":esq -> "nodeB" [color="0.650 0.700 0.700"]
        if (nodo.left != null) {
            sb.append("\"node" + nodo.element + "\":esq -> \"node" + nodo.left.element + "\" \n");
        }

        geraConexoesDOT(sb, nodo.right);
        // "nodeA":dir -> "nodeB";
        if (nodo.right != null) {
            sb.append("\"node" + nodo.element + "\":dir -> \"node" + nodo.right.element + "\" \n");
        }
        // "[label = " << nodo->hDir << "]" <<endl;
    }

    private void geraNodosDOT(StringBuilder sb, Node<E> nodo) {
        if (nodo == null) {
            return;
        }
        geraNodosDOT(sb, nodo.left);
        // node10[label = "<esq> | 10 | <dir> "];
        sb.append("node" + nodo.element + "[label = \"<esq> | " + nodo.element + " | <dir> \"]\n");
        geraNodosDOT(sb, nodo.right);
    }

    public void geraConexoesDOT(StringBuilder sb) {
        geraConexoesDOT(sb, root);
    }

    public void geraNodosDOT(StringBuilder sb) {
        geraNodosDOT(sb, root);
    }

    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    // http://viz-js.com/
    // https://dreampuf.github.io/GraphvizOnline
    public String toDOT() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph g { \nnode [shape = record,height=.1];\n");
        geraNodosDOT(sb);
        geraConexoesDOT(sb);
        sb.append("}\n");
        return sb.toString();
    }
}
