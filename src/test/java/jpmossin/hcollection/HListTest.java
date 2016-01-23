package jpmossin.hcollection;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static jpmossin.hcollection.HCollection.hlist;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class HListTest {

    final HListImpl<Integer> oneToFive = hlist(asList(1, 2, 3, 4, 5));

    @Test
    public void mapAppliesFuncToEachElem() {
        List<Integer> doubled = oneToFive.map(e -> e * 2);
        Iterator<Integer> oneToFiveIt = oneToFive.iterator();
        Iterator<Integer> doubledIt = doubled.iterator();
        while (oneToFiveIt.hasNext()) {
            assertThat(doubledIt.next(), equalTo(2 * oneToFiveIt.next()));
        }
        assertThat(doubled.size(), equalTo(oneToFive.size()));
    }

    @Test
    public void flatMapAppliesFuncToEachElemAndFlattensResult() {
        List<Integer> test = oneToFive.flatMap(e -> asList(e, e * 2));
        assertThat(test.size(), equalTo(2 * oneToFive.size()));
        for (int i=0; i<oneToFive.size(); i++) {
            assertThat(oneToFive.get(i), equalTo(test.get(2 * i)));
            assertThat(2 * oneToFive.get(i), equalTo(test.get(2 * i + 1)));
        }
    }

    @Test
    public void filterShouldKeepOnlyMatchingElements() {
        List<Integer> filteredNums = oneToFive.filter(e -> e % 2 == 0);
        assertThat(filteredNums, equalTo(asList(2, 4)));
    }


    @Test
    public void reduceComputesAReductionOfAllElems() {
        Integer sum = oneToFive.reduce((acc, e) -> acc + e, 0);
        assertThat(sum, equalTo(15));
    }

    @Test
    public void reduceReturnsIdentityOnEmptyList() {
        Integer mult = new HListImpl<Integer>().reduce((acc, e) -> acc * e, 1);
        assertThat(mult, equalTo(1));
    }

    @Test
    public void reduceComputesResultLeftToRight() {
        // for a non-associate function the result will depend on evaluation order
        
    }
}