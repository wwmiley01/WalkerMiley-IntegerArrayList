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
        Node newNode= new Node(val);
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
        Node newNode= new Node(val);
        Node old = getNodeAt(index);
        Node prev = getNodeAt(index-1);
        newNode.next = old;
        prev.next = newNode;
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
    }

    public Integer remove (int index){
        if(index < 0 || index>=size){
            throw new IndexOutOfBoundsException(index + " is not a valid index");
        }
        Integer val = getNodeAt(index).value;
        Node prev = getNodeAt(index-1);
        prev.next = getNodeAt(index).next;
        size--;
        return val;
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
        while(current.next!= null && index<this.size){
            if(current.value != other.get(index)){
                return false;
            }
            current = current.next;
            index++;
        }
        return true;
    }





    public boolean containsRecursive(Integer val){
        
    }

    public String toString(){
        String result = "[";
        Node current = head;
        while (current.next!= null){
            result += current.value + ", ";
            current = current.next;
        }
        result += current.value;
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



