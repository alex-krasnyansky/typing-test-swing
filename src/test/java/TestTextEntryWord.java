import org.akrasnyansky.typetest.recognizer.TextEntryWord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("HardCodedStringLiteral")
public class TestTextEntryWord {

    @SuppressWarnings("DuplicateStringLiteralInspection")
    @Test
    public void testCreation() {
        TextEntryWord w1 = new TextEntryWord("кошка", 6);
        assertEquals(TextEntryWord.TYPE_WORD, w1.getType());
        assertEquals("кошка", w1.getString());
        assertEquals(6, w1.getPositionStart());
        assertEquals(5, w1.getLength());
        assertEquals(11, w1.getPositionEnd());
        // Ё
        TextEntryWord w2 = new TextEntryWord("Ёжик", 0);
        assertEquals("Ежик", w2.getString());
        // ё
        TextEntryWord w3 = new TextEntryWord("Осёл", 0);
        assertEquals("Осел", w3.getString());
    }

    @Test
    public void testDiffs() {
        TextEntryWord w1left = new TextEntryWord("сова", 19);
        assertEquals(0, w1left.getDiffMeasure(w1left));
        assertEquals(4, w1left.getDiffMeasure(null));

        TextEntryWord w1ident = new TextEntryWord("сова", 800);
        assertEquals(0, w1left.getDiffMeasure(w1ident));
        assertEquals(0, w1ident.getDiffMeasure(w1left));

        TextEntryWord w1right = new TextEntryWord("сва", 7);
        assertEquals(1, w1left.getDiffMeasure(w1right));
        assertEquals(1, w1right.getDiffMeasure(w1left));

        TextEntryWord w2left = new TextEntryWord("ива", 54);
        TextEntryWord w2right = new TextEntryWord("иван", 330);
        assertEquals(1, w2left.getDiffMeasure(w2right));
        assertEquals(1, w2right.getDiffMeasure(w2left));

        TextEntryWord w3left = new TextEntryWord("сова", 0);
        TextEntryWord w3right = new TextEntryWord("сотовая", 76);
        assertEquals(3, w3left.getDiffMeasure(w3right));
        assertEquals(3, w3right.getDiffMeasure(w3left));

        TextEntryWord w4left = new TextEntryWord("кар", 7);
        TextEntryWord w4right = new TextEntryWord("икар", 21);
        assertEquals(1, w4left.getDiffMeasure(w4right));
        assertEquals(1, w4right.getDiffMeasure(w4left));

        TextEntryWord w5left = new TextEntryWord("Петр", 900);
        TextEntryWord w5right = new TextEntryWord("Пётр", 12);
        assertEquals(0, w5left.getDiffMeasure(w5right));
        assertEquals(0, w5right.getDiffMeasure(w5left));
    }

}

