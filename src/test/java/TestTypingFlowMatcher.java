import org.akrasnyansky.typetest.recognizer.TextChain;
import org.akrasnyansky.typetest.recognizer.TypingFlowMatcher;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings({"DuplicateStringLiteralInspection", "HardCodedStringLiteral"})
public class TestTypingFlowMatcher {

    private TextChain srcChain;
    private TextChain tgtChain;

    @Before
    public void setUp() throws Exception {
        srcChain = new TextChain();
        tgtChain = new TextChain();
    }

    @Test
    public void testCreation() {
        srcChain.append("Это первый образец текста.");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        assertNotNull(matcher);
        assertEquals(0, matcher.getPercentTyped());
        assertEquals(0, matcher.getTypedChars());
        assertEquals(0, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertNotNull(matcher.getNextToType());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
    }

    @Test
    public void testNotInitialized() {
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        assertNotNull(matcher);
        assertEquals(0, matcher.getPercentTyped());
        assertEquals(0, matcher.getTypedChars());
        assertEquals(0, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertNotNull(matcher.getNextToType());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(0, matcher.getNextToType().length);
    }

    @Test
    public void testTypingFlowAppend() {
        srcChain.append("Это первый образец текста."); // 26 chars, 4 words, 8 elements
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        // Append 1
        matcher.insert("Это п", 0); // 5 chars, 2 words, 3 elements
        assertEquals(37, matcher.getPercentTyped());
        assertEquals(5, matcher.getTypedChars());
        assertEquals(2, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(4, matcher.getNextToType().position);
        assertEquals(6, matcher.getNextToType().length);
        // Append 2
        matcher.insert("ервый о", 5); // 7 chars, (12 chars, 3 words, 5 elements)
        assertEquals(62, matcher.getPercentTyped());
        assertEquals(12, matcher.getTypedChars());
        assertEquals(3, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(11, matcher.getNextToType().position);
        assertEquals(7, matcher.getNextToType().length);
        // Append 3
        matcher.insert("бразец ", 12); // 7 chars, (19 chars, 3 words, 6 elements)
        assertEquals(75, matcher.getPercentTyped());
        assertEquals(19, matcher.getTypedChars());
        assertEquals(3, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(19, matcher.getNextToType().position);
        assertEquals(6, matcher.getNextToType().length);
        // Append final
        matcher.insert("текста.", 19); // 7 chars, (26 chars, 4 words, 8 elements)
        assertEquals(100, matcher.getPercentTyped());
        assertEquals(26, matcher.getTypedChars());
        assertEquals(4, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
    }

    @Test
    public void testMakingExtraGap() {
        srcChain.append("Три слова просто");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        matcher.insert("Три  слова просто", 0);
        assertEquals(100, matcher.getPercentTyped());
        assertEquals(17, matcher.getTypedChars());
        assertEquals(3, matcher.getTypedWords());
        assertEquals(1, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
    }

    @Test
    public void testTypingFlowWithTypos() {
        srcChain.append("На этом работы были приостановлены на два года (если не считать короткое возвращение к этой гофре, когда через неё начало жутко задувать).");
        // 138 chars, 22 words, 47 elements
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        // Append 1
        matcher.insert("На этом работы были приосановлены н два года ( еси не", 0); // 53 chars, 10 words, 20 elements (21 with error els), 4 typos
        assertEquals(53, matcher.getTypedChars());
        assertEquals(10, matcher.getTypedWords());
        assertEquals(4, matcher.getMistypedChars());
        assertEquals(3, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(55, matcher.getNextToType().position);
        assertEquals(1, matcher.getNextToType().length);
        assertEquals(42, matcher.getPercentTyped());
        // Edit
        matcher.insert("л", 49); // 54 chars, 10 words, 20 elements (21 with error els), 3 typos
        assertEquals(54, matcher.getTypedChars());
        assertEquals(10, matcher.getTypedWords());
        assertEquals(3, matcher.getMistypedChars());
        assertEquals(2, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(55, matcher.getNextToType().position);
        assertEquals(1, matcher.getNextToType().length);
        assertEquals(42, matcher.getPercentTyped());
        // Cat walked on the keyboard
        matcher.insert(" считать короткое возвращение к jerjh fswew234 ii", 54); // (29 correct elements)
        assertEquals(103, matcher.getTypedChars());
        assertEquals(17, matcher.getTypedWords());
        assertEquals(20, matcher.getMistypedChars());
        assertEquals(5, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.MONKEY, matcher.getSanity());
        assertEquals(87, matcher.getNextToType().position);
        assertEquals(4, matcher.getNextToType().length);
        assertEquals(72, matcher.getPercentTyped());
        // A human returned
        matcher.insert(" через нее начало жутко задувать).",103);
        assertEquals(137, matcher.getTypedChars());
        assertEquals(22, matcher.getTypedWords());
        assertEquals(39, matcher.getMistypedChars()); // not found + unexpected
        assertEquals(8, matcher.getMistypedWords());  // not found + unexpected
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(2, matcher.getNextToType().length);
        assertEquals(85, matcher.getPercentTyped());
    }

    @Test
    public void testTypingFlowAndRemove() {
        srcChain.append("The following Apache modules must be installed:"); // 47 chars, 7 words, 14 elements
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        // Append 1
        matcher.insert("The following Apache modules must be installed:", 0);
        assertEquals(47, matcher.getTypedChars());
        assertEquals(7, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
        assertEquals(100, matcher.getPercentTyped());
        // Remove 1
        matcher.remove(18, 2); // he - turning into: The following Apac modules must be installed:
        assertEquals(45, matcher.getTypedChars());
        assertEquals(7, matcher.getTypedWords());
        assertEquals(2, matcher.getMistypedChars());
        assertEquals(1, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
        assertEquals(100, matcher.getPercentTyped());
        // Remove 2
        matcher.remove(14, 5); // Apac - turning into: The following modules must be installed:
        assertEquals(40, matcher.getTypedChars());
        assertEquals(6, matcher.getTypedWords());
        assertEquals(7, matcher.getMistypedChars());
        assertEquals(1, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
        assertEquals(85, matcher.getPercentTyped());
        // Insert back "che"
        matcher.insert(" che", 13); // The following che modules must be installed:
        assertEquals(44, matcher.getTypedChars());
        assertEquals(7, matcher.getTypedWords());
        assertEquals(11, matcher.getMistypedChars()); // not found + unexpected
        assertEquals(2, matcher.getMistypedWords()); // not found + unexpected
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
        assertEquals(85, matcher.getPercentTyped());
        // Insert back "Apa"
        matcher.insert("Apa", 14);
        assertEquals(47, matcher.getTypedChars());
        assertEquals(7, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(TypingFlowMatcher.Sanity.HOMOSAPIENS, matcher.getSanity());
        assertEquals(0, matcher.getNextToType().position);
        assertEquals(3, matcher.getNextToType().length);
        assertEquals(100, matcher.getPercentTyped());
    }

    @Test
    public void testMatchWithSeparators() {
        srcChain.append("где лучше — у незнакомца. крикнул: «По-ошла вон, треклятая!»");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        matcher.insert("где лучше - у незнакомца. крикнул: \"По-ошла вон, треклятая!\"", 0);
        assertEquals(60, matcher.getTypedChars());
        assertEquals(9, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(100, matcher.getPercentTyped());
    }

    @Test
    public void testPlatformIndependence1() {
        srcChain.append("строка1\nстрока2");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        matcher.insert("строка1\n\rстрока2", 0);
        assertEquals(2, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(100, matcher.getPercentTyped());
    }

    @Test
    public void testPlatformIndependence2() {
        srcChain.append("строка1\nстрока2");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        matcher.insert("строка1\r\nстрока2", 0);
        assertEquals(2, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(100, matcher.getPercentTyped());
    }

    @Test
    public void testPlatformIndependence3() {
        srcChain.append("строка1\nстрока2");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        matcher.insert("строка1\rстрока2", 0);
        assertEquals(2, matcher.getTypedWords());
        assertEquals(0, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(100, matcher.getPercentTyped());
    }

    @Test
    public void testPlatformIndependence4Wrong() {
        srcChain.append("строка1\nстрока2");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        matcher.insert("строка1\n\nстрока2", 0);
        assertEquals(2, matcher.getTypedWords());
        assertEquals(1, matcher.getMistypedChars());
        assertEquals(0, matcher.getMistypedWords());
        assertEquals(100, matcher.getPercentTyped());
    }

    @Test
    public void testRemoveEdit() {
        srcChain.append("Они не должны");
        TypingFlowMatcher matcher = new TypingFlowMatcher(srcChain, tgtChain);
        matcher.insert("Они не", 0);
        assertEquals(0, matcher.getMistypedChars());
        matcher.insert("ххх", 2); // Онхххи не
        assertEquals(11, matcher.getMistypedChars());
        matcher.remove(2, 3); // Они не
        assertEquals(0, matcher.getMistypedChars());
    }


}
