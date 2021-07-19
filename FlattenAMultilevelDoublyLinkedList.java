import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


/**
 * Definition for a Node.
 */
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;


    @Override
    public String toString() {
        return "(" + this.val + ")"; 
    }
};


/**
 * 
 */
public class FlattenAMultilevelDoublyLinkedList {
    

    /**
     * Traverse linked list forward (head -> tail).
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static void forward(Node head) {

        // **** display all nodes in the linked list ****
        for (Node p = head; p != null; p = p.next) {
            System.out.print(p.toString());
            if (p.next != null)
                System.out.print("->");
        }

        // **** end of linked list ****
        System.out.println();
    }


    /**
     * Traverse linked list backwards (head <- tail).
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static void backward(Node tail) {

        // **** display all nodes in the linked list ****
        for (Node p = tail; p != null; p = p.prev) {
            System.out.print(p.toString());
            if (p.prev != null)
                System.out.print("->");
        }

        // **** start of linked list ****
        System.out.println();
    }


    /**
     * Build multilevel dobly linked list.
     * 
     * !!! NOT PART OF SOLUTION !!!!
     */
    static Node buildLinkedList (Integer[] arr) {

        // **** sanity checks ****
        if (arr == null) return null;

        // **** initialization ****
        Node head   = null;

        Node h      = null;
        Node t      = null;
        Node n      = null;

        // **** process the array creating the linked list ****
        for (int i = 0; i < arr.length; i++) {

            // **** process integer ****
            if (arr[i] != null) {

                // **** create node and set value ****
                Node node   = new Node();
                node.val    = arr[i];

                // **** ****
                if (n != null) {
                    n.child = node;
                    n = null;
                    h = null;
                    t = null;
                }

                // **** this node is a head ****
                if (h == null) {

                    // **** set head and tail of queue ****
                    if (head == null) {
                        head = node;
                    }

                    // **** set head and tail of this linked list ****
                    h = node;
                    t = node;
                }

                // **** this node is not a head ****
                else {

                    // **** update next on previous node ****
                    t.next = node;

                    // **** update node links ****
                    node.prev = t;

                    // **** update tail ****
                    t = node;
                }
            } 
            
            // **** process null ****            
            else {

                // **** first n ****
                if (n == null)
                    n = h;

                // **** next n ****
                else
                    n = n.next;
            }
        }

        // **** return head ****
        return head;
    }



    /**
     * Flatten the list so that all the nodes appear in a single-level, doubly linked list.
     * You are given the head of the first level of the list.
     * 
     * Runtime: 1 ms, faster than 17.76% of Java online submissions.
     * Memory Usage: 37 MB, less than 58.65% of Java online submissions.
     */
    static Node flatten0(Node head) {

        // **** traverse main list until end ****
        for (Node p = head; p != null; p = p.next) {

            // **** check if this node has a child list ****
            if (p.child != null) {

                // **** ****
                Node child = p.child;

                // **** to avoid endless loop ****
                p.child = null;

                // **** ****
                child = flatten0(child);

                // **** set `t` to tail in child linked list ****
                Node t = child;
                for ( ; t.next != null; t = t.next);

                // **** splice child linked list AFTER `p` node ****
                t.next      = p.next;
                if (p.next != null)
                    p.next.prev = t;

                p.next      = child;
                child.prev  = p;
            }
        }

        // **** return head node of list ****
        return head;
    }


    /**
     * Recursive call.
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions.
     * Memory Usage: 38.3 MB, less than 25.33% of Java online submissions.
     * 
     * Execution: O(n) - Space: O(1)
     */
    static private Node flattenRecursive(Node head) {

        // **** end condition ****
        if (head == null || (head.next == null && head.child == null)) return head;
        
        // **** recursive calls ****
        Node ct = flattenRecursive(head.child);
        Node nt = flattenRecursive(head.next);
        
        // **** ****
        if (ct != null) {
            if (nt != null) {

                // **** link after flatten child list ****
                ct.next         = head.next;
                head.next.prev  = ct;
                ct              = nt;
            }

            // **** link flatten child list ****
            head.next       = head.child;
            head.next.prev  = head;
            head.child      = null;

            // **** return child tail ****
            return ct;
        } else {

            // **** return next tail ****
            return nt;
        }
    }


    /**
     * Flatten the list so that all the nodes appear in a single-level, doubly linked list.
     * You are given the head of the first level of the list.
     * 
     * 26 / 26 test cases passed.
     * Status: Accepted
     * Runtime: 0 ms
     * Memory Usage: 38.3 MB
     */
    static Node flatten(Node head) {
       
        // **** flatten list (recursively) ****
        flattenRecursive(head);
        
        // **** return head node ****
        return head;
    }


    /**
     * Test scaffold.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** initialization ****
        Integer[] arr = null;

        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        // ???? does not support null values ????
        // Integer[] arr = Arrays.stream(br.readLine().trim().split(","))
        //                     .mapToInt(Integer::parseInt)
        //                     .boxed()
        //                     .toArray(Integer[]::new);

        // ???? supports null values ????
        // Integer[] arr = Arrays.stream(br.readLine().trim().split(","))
        //                     // .mapToInt(x -> ((x == null) ? (Integer)null : Integer.parseInt(x)))
        //                     .map( x -> ((x == null) ? (Integer)null : Integer.parseInt(x)) )
        //                     // .boxed()
        //                     .toArray(Integer[]::new);   


        // **** populate String[] with input values ****
        String[] strs = br.readLine().trim().split(",");

        // ???? ????
        // System.out.println("main <<< strs.length: " + strs.length);
        // for (int i = 0; i < strs.length; i++)
        //     System.out.println("main <<< strs[" + i + "] ==>" + strs[i] + "<==");

        // **** allocate and populate arr ****
        if (!(strs.length == 1 && strs[0].equals(""))) {

            // **** allocate arr ****
            arr = new Integer[strs.length];

            // **** populate arr ****
            for (int i = 0; i < arr.length; i++) {
                if (strs[i].equals("null"))
                    arr[i] = null;
                else
                    arr[i] = Integer.parseInt(strs[i]);
            }
        }

        // **** close buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<< arr: " + Arrays.toString(arr));

        // **** create and populate multilevel doubly linked list ****
        Node head = buildLinkedList(arr);
    
        // // **** flatten the linked list ****
        // head = flatten0(head);

        // // **** display flatten linked list ****
        // System.out.print("main <<< head: "); forward(head);

        // **** flatten the linked list ****
        head = flatten(head);

        // **** display flatten linked list ****
        System.out.print("main <<< head: "); forward(head);
    }
}