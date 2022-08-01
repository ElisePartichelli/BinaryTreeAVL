import java.util.Random;

//André Menezes Bins 21200695-1
//Marcos do Nascimento Ferreira 21200886-6
//Yanny Elise Partichelli 21200661-3

public class App {
    public static void main(String[] args) {
        BinarySearchTree<Integer> B = new BinarySearchTree<Integer>();
       
        B.add(10);
        B.add(6);
        B.add(12);
        B.add(11);
        for(int i=15;i<25;i++)
            B.add(i);   
            
        System.out.println(B.toDOT());
        B.ApplyBalancing();
        System.out.println(B.toDOT());
        

        System.out.println(B.isBalancedTree());
    }

}

