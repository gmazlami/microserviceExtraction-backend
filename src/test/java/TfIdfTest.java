import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf.TfIdfWrapper;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by gmazlami on 12/6/16.
 */
public class TfIdfTest {
    private List<Collection<String>> documents;

    private Set<String> document1Terms = ImmutableSet.of(
            "to", "be", "or", "not", "to", "be",
            "to be or", "be or not", "or not to", "not to be"
    );

    private Set<String> document2Terms = ImmutableSet.of(
            "or", "to", "jump",
            "or to jump"
    );

    @Before
    public void setUp() {

    }

    @Test
    public void similarity() {
        List<String> doc1 = new ArrayList<>();
        doc1.add("this");
        doc1.add("is");
        doc1.add("a");
        doc1.add("test");
        doc1.add("sentence");

        List<String> doc2 = new ArrayList<>();
        doc2.add("that");
        doc2.add("is");
        doc2.add("blabla");
        doc2.add("real");
        doc2.add("document");

        System.out.println(TfIdfWrapper.computeSimilarity(doc1,doc2));
        assertEquals(1,1);
    }


}
