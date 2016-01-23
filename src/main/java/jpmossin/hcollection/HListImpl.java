package jpmossin.hcollection;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;


public class HListImpl<E> implements HList<E> {

    private final List<E> wrappedList;

    public HListImpl(List<E> list) {
        this.wrappedList = list;
    }

    public HListImpl() {
        this.wrappedList = new LinkedList<>();
    }


    @Override
    public <R> HListImpl<R> map(Function<? super E, ? extends R> func) {
        HListImpl<R> mappedList = empty();
        this.forEach(e ->
                mappedList.add(func.apply(e)));
        return mappedList;
    }


    @Override
    public <R> HListImpl<R> flatMap(Function<? super E, List<? extends R>> func) {
        HListImpl<R> mappedList = empty();
        this.forEach(e ->
                mappedList.addAll(func.apply(e)));
        return mappedList;
    }


    @Override
    public HListImpl<E> filter(Function<? super E, Boolean> predicate) {
        HListImpl<E> filteredList = empty();
        this.forEach(e -> {
            if (predicate.apply(e)) {
                filteredList.add(e);
            }
        });
        return filteredList;
    }


    @Override
    public Optional<E> find(Function<? super E, Boolean> predicate) {
        for (E e : wrappedList) {
            if (predicate.apply(e)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean all(Function<? super E, Boolean> predicate) {
        for (E e : wrappedList) {
            if (!predicate.apply(e)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public <R> R reduce(BiFunction<? super R, ? super E, ? extends R> reducer, R identity) {
        R reducedValue = identity;
        for (E elem : this) {
            reducedValue = reducer.apply(reducedValue, elem);
        }
        return reducedValue;
    }


    public <R> Map<R, HList<E>> groupBy(Function<? super E, ? extends R> keyFunc) {
        Map<R, HList<E>> grouped = new HashMap<>();
        this.forEach(e -> {
            R key = keyFunc.apply(e);
            HList<E> elemGroup = grouped.getOrDefault(key, new HListImpl<>());
            elemGroup.add(e);
            grouped.put(key, elemGroup);
        });
        return grouped;
    }



    @Override
    public int size() {
        return wrappedList.size();
    }

    @Override
    public boolean isEmpty() {
        return wrappedList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return wrappedList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return wrappedList.iterator();
    }

    @Override
    public Object[] toArray() {
        return wrappedList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return wrappedList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return wrappedList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return wrappedList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return wrappedList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return wrappedList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return wrappedList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return wrappedList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return wrappedList.retainAll(c);
    }

    @Override
    public void clear() {
        wrappedList.clear();
    }

    @Override
    public E get(int index) {
        return wrappedList.get(index);
    }

    @Override
    public E set(int index, E element) {
        return wrappedList.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        wrappedList.add(index, element);
    }

    @Override
    public E remove(int index) {
        return wrappedList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return wrappedList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return wrappedList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return wrappedList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return wrappedList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return wrappedList.subList(fromIndex, toIndex);
    }

    @Override
    public boolean equals(Object obj) {
        return wrappedList.equals(obj);
    }

    @Override
    public int hashCode() {
        return wrappedList.hashCode();
    }

    @Override
    public String toString() {
        return "hlist(" + wrappedList.toString() + ")";
    }

    private <A> HListImpl<A> empty() {
        if (wrappedList instanceof LinkedList) {
            return new HListImpl<>(new LinkedList<>());
        }
        else {
            return new HListImpl<>(new ArrayList<>());
        }
    }

}
