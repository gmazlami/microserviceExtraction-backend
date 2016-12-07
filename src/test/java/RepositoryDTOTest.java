import ch.uzh.ifi.seal.monolith2microservices.dtos.RepositoryDTO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by gmazlami on 12/7/16.
 */
public class RepositoryDTOTest {

    private RepositoryDTO repositoryDTO;

    @Before
    public void setUp(){
        repositoryDTO = new RepositoryDTO();
        repositoryDTO.setUri("https://github.com/feincms/feincms.git");
    }

    @Test
    public void testGetName(){
        assertEquals("feincms",repositoryDTO.getName());
    }
}
