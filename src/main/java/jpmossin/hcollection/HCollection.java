package jpmossin.hcollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HCollection {

    public static <E> HListImpl<E> hlist(List<E> list) {
        return new HListImpl<>(list);
    }


    /**
     * Creates a list where the i'th element is a list containing all the i'th
     * elements of the input lists.
     * The size of the created list will be equal to the size of the smaller
     * of the input lists.
     * zip([1, 2, 3], [10, 11]) = [[1, 10], [2, 11]]
     *
     * @param lists lists to zip
     * @param <R>   The element type of the created list
     * @return the zipped list
     */
    @SafeVarargs
    public static <R> HList<HList<R>> zip(List<? extends R>... lists) {
        HList<HList<R>> zipped = new HListImpl<>();
        List<Iterator<? extends R>> iterators = new ArrayList<>();
        for (List<? extends R> list : lists) {
            iterators.add(list.iterator());
        }
        while (true) {
            List<R> zippedEntry = new ArrayList<>();
            for (Iterator<? extends R> it : iterators) {
                if (!it.hasNext()) {
                    return zipped;
                }
                zippedEntry.add(it.next());
            }
            zipped.add(new HListImpl<>(zippedEntry));
        }
    }

}
