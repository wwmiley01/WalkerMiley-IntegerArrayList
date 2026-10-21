public class IntegerLinkedList implements IntegerList{
    private Node head;
    private int size;

    public IntegerLinkedList(){
        head = null;
    }

    private Node getNodeAt(int index){
        if(index<0||index>=size){
            throw new IndexOutOfBoundsException("Invalid index" + index);
        }
        Node current = head;
        for (int i = 0; i<index; i++){
            current = current.next;
        }
        return current;
    }

    public void add(Integer val){
        Node newNode = new Node(val);
        if(size == 0){
            head = newNode;
        }
        else{
            Node last = getNodeAt(size-1);
            last.next = newNode;
        }
        size++;
    }

    public void add(int index, Integer val){
        if(index < 0 || index>size){
            throw new IndexOutOfBoundsException(index + " is not a valid index");
        }
        Node newNode = new Node(val);
        if(index==0){
            newNode.next = head;
            head = newNode;
        }
        else{
            Node prev = getNodeAt(index-1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    public void set(int index, Integer val){
        if(index<size){
            Node oldNode = getNodeAt(index);
            oldNode.value = val;
        }
        else{
            throw new IndexOutOfBoundsException("Invalid Index" + index);
        }

    }

    public void clear(){
        head = null;
        size=0;
    }

    public Integer remove (int index){
        if(index < 0 || index>=size){
            throw new IndexOutOfBoundsException(index + " is not a valid index");
        }
        Integer val = getNodeAt(index).value;
        if(index == 0){
            head = head.next;
            size--;
            return val;
        }
        Node prev = getNodeAt(index-1);
        prev.next = prev.next.next;
        size--;
        return val;
    }

    public int indexOf(Integer val){
        Node checkNode = new Node(val);
        Node current = head;
        int index = 0;
        while(current!= null){
            if(current.value.equals(checkNode.value)){
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public Integer get(int i){
        if (i < 0 || i>=size){
            throw new IndexOutOfBoundsException("index " + i + " is out of bounds.");
        }
        return getNodeAt(i).value;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public boolean contains(Integer val){
        return indexOf(val) != -1;
    }

    public boolean equals(List<Integer> other){
        if(this.size() != other.size()){
            return false;
        }
        Node current = head;
        int index = 0;
        while(current!= null){
            if(!current.value.equals(other.get(index))){
                return false;
            }
            current = current.next;
            index++;
        }
        return true;
    }

    public int countItterative(){
        int count = 0;
        Node current = head;
        while(current.next !=null){
            current = current.next;
            count++;
        }
        return count;
    }

    private int countFrom(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + countFrom(node.next);
    }

    public int countRecursive() {
        return countFrom(head);
    }


    public boolean containsRecursive(Integer val){
        return containsRecursiveHelper(head,val);
    }

    private boolean containsRecursiveHelper(Node current, Integer val){
        if(current == null){
            return false;
        }
        if(current.value == val || (current.value!=null && current.value.equals(val))){
            return true;
        }
        return containsRecursiveHelper(current.next, val);
    }

    public String toStringReversed(){
        String result = "[";
        for(int i = size-1; i>=0; i--){
            result += get(i);
            if(i>0){
                result +=", ";
            }
        }
        result +="]";
        return result;
    }

    public String toString(){
        String result = "[";
        Node current = head;
        while (current != null && current.next != null){
            result += current.value + ", ";
            current = current.next;
        }
        if(current != null){
            result += current.value;
        }
        result += "]";
        return result;
    }
    public static void main (String[] args){
        IntegerLinkedList list1 =new IntegerLinkedList();
        list1.add(10);
        list1.add(10);
        list1.add(17);
        list1.add(12);
        list1.add(4,5);

        System.out.println(list1);
        System.out.println((list1.indexOf(17)));
    }
}



