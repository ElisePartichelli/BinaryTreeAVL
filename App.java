public class App {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
       
        tree.add(10);
        tree.add(6);
        tree.add(12);
        tree.add(11);
        for(int i=15;i<25;i++)
            tree.add(i);   
            
        System.out.println(tree.toDOT());
        tree.applyBalancing();
        System.out.println(tree.toDOT());
        

        System.out.println(tree.isBalancedTree());
    }

}

