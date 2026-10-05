public interface List<E> {
    public void add(E val);// (adds to the end of the list.  If necessary, handles the creation of a larger internal array, and copies values from the old array to the new one)
    public void add(int index, E val);// (which inserts at position index, and shifts everything else to the right as necessary.  If necessary, handles the creation of a larger internal array, and copies values from the old array to the new one.  Should only allow adding to positions 0 through size.  Otherwise, should throw in IndexOutOfBoundsException.)
    public void set(int index, E val);// (which replaces the element at position index with x.)
    public void clear();// (results in .size() returning 0) and .get(0) throwing a new IndexOutOfBoundsException()
    public E remove(int index);// (removes the value at position index.  Shifts everything else to the left as necessary.  Should only allow removal from a valid index in the list.)
    public E get(int index);// (returns the value at position index, or throws a new IndexOutOfBoundsException)
    public int size();// (returns the number of items in the list.  -- NOT the capacity of the array, but the number of values intentionally placed in the list)
    public boolean isEmpty();//(returns true if .size() is 0)
    public boolean contains(E val);// (returns true if the IntegerArrayList contains the given val, false otherwise)
    public int indexOf(E val);// (returns the index of the first instance of val that appears in the IntegerArrayList, or -1 if it does not appear)
    public boolean equals(List<E> other);// (returns true if this IntegerArrayList and the other IntegerArrayList have all of the same elements in the same order.
    public String toString();// returns a nice representation of all of the elements in the IntegerArrayList with commons, and square brackets.
}
