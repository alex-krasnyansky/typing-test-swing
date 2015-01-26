import org.akrasnyansky.typetest.TextSampleRepository;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

public class TestTextSampleRepository {

    @Test
    public void testExtractFragment0() {
        TextSampleRepository repToTest = (TextSampleRepository) TextSampleRepository.getInstance();
        Map<Integer,TextSampleRepository.HeaderAndText> texts = repToTest.getTexts();
        assertTrue(texts.keySet().size() > 0);
        assertTrue(texts.entrySet().size() > 0);
        TextSampleRepository.HeaderAndText strugatzky = texts.get(0);
        assertNotNull(strugatzky);
        assertEquals("А. и Б. Стругацкие. Трудно быть богом.", strugatzky.header);
        assertTrue(strugatzky.text.length() > 100);
    }

}
