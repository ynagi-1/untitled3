public class MyLinkedList<T> implements MyList<T> {
    private class MyNode {
        T value;
        MyNode next;
        MyNode prev;

        MyNode(T value) {
            this.value = value;
        }
    }

    private MyNode head;
    private MyNode tail;
    private int size;

    public void add(T item) {
        addLast(item);
    }

    public void addFirst(T item) {
        MyNode newNode = new MyNode(item);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T item) {
        MyNode newNode = new MyNode(item);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void add(int index, T item) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            addFirst(item);
        } else if (index == size) {
            addLast(item);
        } else {
            MyNode newNode = new MyNode(item);
            MyNode current = getNode(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    public void set(int index, T item) {
        getNode(index).value = item;
    }

    public T get(int index) {
        return getNode(index).value;
    }

    public T getFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        return head.value;
    }

    public T getLast() {
        if (tail == null) throw new IllegalStateException("List is empty");
        return tail.value;
    }

    public void remove(int index) {
        MyNode node = getNode(index);
        unlink(node);
    }

    public void removeFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        unlink(head);
    }

    public void removeLast() {
        if (tail == null) throw new IllegalStateException("List is empty");
        unlink(tail);
    }

    private void unlink(MyNode node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;
        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
        size--;
    }

    private MyNode getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        MyNode current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current;
    }

    public void sort() {
        Object[] arr = toArray();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                Comparable<T> a = (Comparable<T>) arr[j];
                T b = (T) arr[j + 1];
                if (a.compareTo(b) > 0) {
                    Object tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        clear();
        for (Object o : arr) {
            addLast((T) o);
        }
    }

    public int indexOf(Object object) {
        int index = 0;
        for (MyNode node = head; node != null; node = node.next) {
            if (node.value.equals(object)) return index;
            index++;
        }
        return -1;
    }

    public int lastIndexOf(Object object) {
        int index = size - 1;
        for (MyNode node = tail; node != null; node = node.prev) {
            if (node.value.equals(object)) return index;
            index--;
        }
        return -1;
    }

    public boolean exists(Object object) {
        return indexOf(object) != -1;
    }

    public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0;
        for (MyNode node = head; node != null; node = node.next) {
            arr[i++] = node.value;
        }
        return arr;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            MyNode current = head;

            public boolean hasNext() {
                return current != null;
            }

            public T next() {
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
