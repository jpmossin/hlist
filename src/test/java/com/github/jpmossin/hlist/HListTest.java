package com.github.jpmossin.hlist;

import com.github.jpmossin.hlist.HList.Tuple2;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.jpmossin.hlist.HListImpl.hlist;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HListTest {

    final HList<Integer> oneToFour = hlist(asList(1, 2, 3, 4));

    @Test
    public void mapAppliesFuncToEachElem() {
        List<Integer> doubled = oneToFour.map(e -> e * 2);
        assertThat(doubled, equalTo(asList(2, 4, 6, 8)));
    }

    @Test
    public void flatMapAppliesFuncToEachElemAndFlattensResult() {
        List<Integer> mapped = oneToFour.flatMap(e -> asList(e, e * 2));
        assertThat(mapped, equalTo(asList(1, 2, 2, 4, 3, 6, 4, 8)));
    }

    @Test
    public void filterShouldKeepOnlyMatchingElements() {
        List<Integer> filteredNums = oneToFour.filter(e -> e % 2 == 1);
        assertThat(filteredNums, equalTo(asList(1, 3)));
    }


    @Test
    public void reduceComputesAReductionOfAllElems() {
        Integer sum = oneToFour.reduce((acc, e) -> acc + e, 0);
        assertThat(sum, equalTo(10));
    }

    @Test
    public void reduceReturnsIdentityOnEmptyList() {
        Integer mult = new HListImpl<Integer>().reduce((acc, e) -> acc * e, 1);
        assertThat(mult, equalTo(1));
    }

    @Test
    public void reduceComputesResultLeftToRight() {
        // for a non-associative function the result will depend on evaluation order
        Integer sumAsc = oneToFour.reduce((acc, e) -> e - acc, 0);
        assertThat(sumAsc, equalTo(2));
        Integer sumDesc = new HListImpl<>(asList(4, 3, 2, 1)).reduce((acc, e) -> e - acc, 0);
        assertThat(sumDesc, equalTo(-2));
    }

    @Test
    public void findReturnsFirstFoundElemWhenMatch() {
        Optional<Integer> found = oneToFour.find(e -> e % 2 == 0);
        assertThat(found.get(), equalTo(2));
    }

    @Test
    public void findReturnsEmptyOptionalWhenNoMatch() {
        Optional<Integer> found = oneToFour.find(e -> e % 2 == 10);
        assertThat(found.isPresent(), is(false));
    }

    @Test
    public void allReturnsTrueWhenAllElemsMatch() {
        boolean allLessThanTen = oneToFour.all(e -> e < 10);
        assertThat(allLessThanTen, is(true));
    }

    @Test
    public void allReturnsFalseIfNotAllElemsMatch() {
        boolean allLessThanThree = oneToFour.all(e -> e < 4);
        assertThat(allLessThanThree, is(false));
    }

    @Test
    public void groupByCorrectlyGroupsElemsByKey() {
        Map<Integer, HList<Integer>> grouped = oneToFour.groupBy(e -> e % 2);
        assertThat(grouped.size(), equalTo(2));
        assertThat(grouped.get(0), equalTo(asList(2, 4)));
        assertThat(grouped.get(1), equalTo(asList(1, 3)));
    }

    @Test
    public void zipCreatesTheCorrectPairsOfElements() {
        List<Integer> multByTen = oneToFour.map(e -> e * 10);
        List<Tuple2<Integer, Integer>> zipped = oneToFour.zip(multByTen);
        zipped.forEach(tuple ->
                assertThat(tuple.second, equalTo(tuple.first * 10))
        );
    }

    @Test
    public void lengthOfZippedListIsEqualToShorter() {
        assertThat(oneToFour.zip(asList(1, 2)).size(), equalTo(2));
    }

}