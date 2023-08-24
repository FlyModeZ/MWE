package fr.alexdoru.megawallsenhancementsmod.hackerdetector.data;

public class SampleList<T> {

    private final Object[] data;
    /** The maximum size */
    private final int capacity;
    /** The current size of the list */
    private int size;
    /** The array index of the last element inserted */
    private int latestIndex;

    public SampleList(int capacity) {
        if (capacity < 2) {
            throw new IllegalArgumentException("Size must be at least 2");
        }
        this.data = new Object[capacity];
        this.capacity = capacity;
        this.size = 0;
        this.latestIndex = -1;
    }

    public void add(T f) {
        this.latestIndex = (this.latestIndex + 1) % this.capacity;
        this.data[this.latestIndex] = f;
        if (this.size < this.capacity) this.size++;
    }

    /**
     * get(0) will return the latest element insert,
     * get(capacity - 1) will return the oldest element
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index > this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        final int i = this.latestIndex - index;
        return (T) this.data[i < 0 ? i + this.capacity : i];
    }

    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.data[i] = null;
        }
        this.size = 0;
        this.latestIndex = -1;
    }

    public int size() {
        return this.size;
    }

    public boolean hasCollected() {
        return size == capacity;
    }

}
