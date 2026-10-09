public class IntegerLinkedList implements IntegerList{
    private Node head;
    private int size;

    public IntegerLinkedList(){
        head = null;
    }

    public void add(Integer val){
        Node newNode= new Node(val);
        if(size == 0){
            head = newNode;
        }
        else{
            Node current = head;
            while(current.next != null){
                current = current.next;
            }
            current.next = newNode;
            size++;
        }
    }

    public int indexOf(Integer val){
        Node checkNode = new Node(val);
        Node current = head;
        int index = 0;
        while(current.next!= null){
            if(current.value.equals(checkNode.value)){
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public String toString(){
        String result = "[";
        Node current = head;
        while (current.next!= null){
            result += current.value + ", ";
            current = current.next;
        }

        result += "]";
        return result;
    }
    public static void main (String[] args){
        IntegerLinkedList list1 =new IntegerLinkedList();
        list1.add(10);
        list1.add(10);
        list1.add(10);
        list1.add(10);

        System.out.println(list1);
        System.out.println((list1.indexOf(17)));
    }
}



