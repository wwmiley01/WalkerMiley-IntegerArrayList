public class IntegerArrayList implements IntegerList{
    private Integer[] values;
    private int capacity;
    private int size;
    public IntegerArrayList(){
        values = new Integer[10];
        size = 0;
    }
    public void add(Integer val){
        if(size == values.length){
            Integer[] newArray = new Integer[values.length*2];
            for (int i = 0; i<values.length; i++){
                newArray[i] = values[i];
            }
            values = newArray;
        }

        values[size] = val;
        size++;
    }
    public int size(){
        return size;
    }
    public void set(int index, Integer val){
        if(index<size){
            values[index] = val;
        }
        else{
            throw new IndexOutOfBoundsException("Invalid Index" + index);
        }

    }
    public void clear(){
        size = 0;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public String toString(){
        String result = "[";
        for (int i = 0; i<size-1; i++){
            result += values[i]+ ", ";
        }
        result += values[size - 1]+ "]";
        return result;
    }
}
