public class IntegerArrayList implements IntegerList{
    private Integer[] values;
    private int capacity;
    private int size;
    public IntegerArrayList(){
        values = new Integer[10];
        size = 0;
    }

    private void resize(){
            Integer[] newArray = new Integer[values.length*2];
            for (int i = 0; i<values.length; i++){
                newArray[i] = values[i];
            }
            values = newArray;
    }

    public void add(Integer val){
        if (size == values.length){
            resize();
        }
        values[size] = val;
        size++;
    }

    public void add (int index, Integer val){
        if(index < 0 || index>size){
            throw new IndexOutOfBoundsException(index + " is not a valid index");
        }
        if (size == values.length){
            resize();
        }
        for (int i = size; i>index; i--){
            values[i] = values[i-1];
        }
        values[index] = val;
    }

    public Integer remove (int index){
        if(index < 0 || index>size){
            throw new IndexOutOfBoundsException(index + " is not a valid index");
        }
        Integer val = values[index];
        values[index] = null;
        for (int i = size; i>index; i--){
            values[i-1] = values[i];
        }
        size--;
        return val;
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
    public int indexOf(Integer val){
        for(int i = 0; i < size; i++){
            if(values[i].equals(val)){
                return i;
            }
        }
        return -1;
    }
    public boolean contains(Integer val){
        return indexOf(val) !=-1;
    }

    public Integer get(int i){
        if (i < 0 || i>=size){
            throw new IndexOutOfBoundsException("index " + i + " is out of bounds.");
        }
        return values[i];
    }
}
