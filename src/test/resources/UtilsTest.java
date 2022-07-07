import com.qa.ims.IMS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    @Mock
    private IMS ims = new IMS();
    @InjectMocks
    private Utils utils = new Utils();



    @Test
    public void testLong() {
        String input = "5";
        Long output = 5L;
        Mockito.when(utils.getString()).thenReturn(input);
        Mockito.when(utils.scanner.nextLine())
        utils.getLong();
        assertEquals(output, utils.getLong());

        Mockito.verify(utils, Mockito.times(1)).getString();
    }
}
