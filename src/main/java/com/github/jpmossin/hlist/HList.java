package com.github.jpmossin.hlist;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A list with some useful Higher-order functions
 * @param <E> Element type
 */
public interface HList<E> extends List<E> {

    /**
     * Create a new list consisting of the result of applying a
     * function to the elements of this list.
     *
     * @param func Function to apply to each element
     * @param <R>  The element type of the new list
     * @return the new list
     */
    <R> HList<R> map(Function<? super E, ? extends R> func);

    /**
     * Create a new list by applying a function to transform each element
     * in this list into a list and flattening the result.
     *
     * @param func Function to apply to each element
     * @param <R>  The element type of the new list
     * @return the new list
     */
    <R> HList<R> flatMap(Function<? super E, List<? extends R>> func);

    /**
     * Create a new list consisting of the elements of this list that match
     * the given predicate.
     *
     * @param predicate Function to apply to each element to determine if it
     *                  should be included
     * @return the new list
     */
    HList<E> filter(Function<? super E, Boolean> predicate);


    /**
     * Find an element in this list that satisfies the given predicate.
     *
     * @param predicate Predicate to check for
     * @return Optional containing the first element satisfying
     * the predicate, or empty Optional if no such element
     */
    Optional<E> find(Function<? super E, Boolean> predicate);


    /**
     * Check if all elements in this list satisfy the given predicate
     *
     * @param predicate Predicate to apply to each element
     * @return true if all elements satisfy the predicate, false if not
     */
    boolean all(Function<? super E, Boolean> predicate);

    /**
     * Computes the value of
     * reducer(..reducer(reducer(identity, e_1), e_2)..e_n),
     * where e_i is the i'th element of this list
     *
     * @param reducer  Function for
     * @param identity The identity value of the reducer function
     * @param <R>      The element type of the reduced value
     * @return the result of the reduction
     */
    <R> R reduce(BiFunction<? super R, ? super E, ? extends R> reducer, R identity);


    /**
     * Groups the elements of this list into a map of lists,
     * keyed using the given key-function.
     *
     * @param keyFunc Function for computing each element's key
     * @param <R>     Key type
     * @return The map of keyed lists
     */
    <R> Map<R, HList<E>> groupBy(Function<? super E, ? extends R> keyFunc);


}
