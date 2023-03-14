package mission;

public interface SimpleList<E> {

    boolean add(E value);

    void add(int index, E value);

    E set(int index, E value);

    E get(int index);

    boolean contains(E value);

    int indexOf(E value);

    int size();

    boolean isEmpty();

    boolean remove(E value);

    E remove(int index);

    void clear();

    static <T> SimpleList<T> fromArrayToList(T[] arrays) {
        final SimpleArrayList<T> tSimpleArrayList = new SimpleArrayList<>();
        for (T array : arrays) {
            tSimpleArrayList.add(array);
        }
        return tSimpleArrayList;
    }

    static <T extends Number> double sum(SimpleList<T> values) {
        double sum = 0.0;

        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i).doubleValue();

        }

        return sum;
    }

    static <T extends Number> SimpleList<T> filterNegative(SimpleList<T> values) {
        SimpleList<T> tmpList = new SimpleArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).doubleValue() >= 0) {
                tmpList.add(values.get(i));
            }
        }
        return tmpList;
    }

    static void copy(SimpleList<? extends Printer> child, SimpleList<? super Printer> parent) {
        // Laser<>   Print<>

        parent.clear();
        for (int i = 0; i < child.size(); i++) {
            parent.add(child.get(i));
        }
    }
}
