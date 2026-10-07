public class Node {
    public Integer value;
    public Node next;

    public Node (Integer value){
        this.value = value;
        next = null;
    }
    public static void main(String[] args){
        Node head = new Node(3);
        Node body = new Node(7);
        Node tail = new Node(10);
        Node current = head;
        head.next = body;
        body.next = tail;
        while(current != null){
            System.out.println(current.value);
            current = current.next;
        }
    }
}
