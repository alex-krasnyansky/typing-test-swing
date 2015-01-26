import org.akrasnyansky.typetest.recognizer.TextChain;
import org.akrasnyansky.typetest.recognizer.TextEntry;
import org.akrasnyansky.typetest.recognizer.TextEntrySeparator;
import org.akrasnyansky.typetest.recognizer.TextEntryWord;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

@SuppressWarnings("HardCodedStringLiteral")
public class TestTextChain {

    @Test
    public void testCreation() {
        TextChain chain = new TextChain("Мама мыла раму.");
        List<TextEntry> items = chain.getChain();
        assertNotNull(items);
        assertEquals(6, chain.size());
        assertEquals(TextEntry.TYPE_WORD, items.get(0).getType());
        assertEquals("Мама", items.get(0).getString());
        assertEquals(TextEntry.TYPE_SEPARATOR, items.get(1).getType());
        assertEquals(" ", items.get(1).getString());
        assertEquals(TextEntry.TYPE_WORD, items.get(2).getType());
        assertEquals("мыла", items.get(2).getString());
        assertEquals(5, items.get(2).getPositionStart());
        assertEquals(9, items.get(2).getPositionEnd());
        assertEquals(TextEntry.TYPE_SEPARATOR, items.get(3).getType());
        assertEquals(" ", items.get(3).getString());
        assertEquals(9, items.get(3).getPositionStart());
        assertEquals(TextEntry.TYPE_WORD, items.get(4).getType());
        assertEquals("раму", items.get(4).getString());
        assertEquals(TextEntry.TYPE_SEPARATOR, items.get(5).getType());
        assertEquals(".", items.get(5).getString());
    }

    @Test
    public void testAjacentSeparators() {
        TextChain chain = new TextChain("- Обалдеть, - выдал я фразу.");
        List<TextEntry> items = chain.getChain();
        assertEquals(TextEntry.TYPE_SEPARATOR, items.get(3).getType());
        assertEquals(",", items.get(3).getString());
        assertEquals(TextEntry.TYPE_SEPARATOR, items.get(4).getType());
        assertEquals(" ", items.get(4).getString());
        assertEquals(TextEntry.TYPE_SEPARATOR, items.get(5).getType());
        assertEquals("-", items.get(5).getString());
        assertEquals(TextEntry.TYPE_SEPARATOR, items.get(6).getType());
        assertEquals(" ", items.get(6).getString());
    }

    @Test
    public void testOperationsInBetween() {
        TextChain chain = new TextChain("Что за");
        // append since 3rd
        assertEquals(3, chain.append(" ерунда здесь творится?"));
        List<TextEntry> mutation1 = chain.getChain();
        assertEquals(10, chain.size());
        assertEquals("творится", mutation1.get(8).getString());
        assertEquals("Что за ерунда здесь творится?", chain.toString());
        // insert in-between words, since 6th
        assertEquals(5, chain.insert(", в самом деле,", 13));
        assertEquals(18, chain.size());
        assertEquals("Что за ерунда, в самом деле, здесь творится?", chain.toString());
        // remove of whole words
        assertEquals(0, chain.remove(0, 7));
        assertEquals(14, chain.size());
        assertEquals("ерунда, в самом деле, здесь творится?", chain.toString());
    }

    @Test
    public void testOperationsAdvanced1()
    {
        // smart append
        TextChain chain1 = new TextChain("Мы начина");
        assertEquals(2, chain1.append("ем КВН!"));
        assertEquals(6, chain1.size());
        assertEquals("начинаем", chain1.getChain().get(2).getString());
        assertEquals(12, chain1.getChain().get(4).getPositionStart());
        assertEquals(15, chain1.getChain().get(4).getPositionEnd());
    }

    @SuppressWarnings("DuplicateStringLiteralInspection")
    @Test
    public void testOperationsAdvanced2() {
        // insert in the middle of the word
        TextChain chain2 = new TextChain("Яркая картина на стене.");
        assertEquals("Яркая картина на стене.", chain2.toString());
        assertEquals(2, chain2.insert("к", 12));
        assertEquals(8, chain2.size());
        assertEquals("Яркая картинка на стене.", chain2.toString());
        assertEquals(15, chain2.getChain().get(4).getPositionStart());
        assertEquals(17, chain2.getChain().get(4).getPositionEnd());
    }

    @Test
    public void testOperationsAdvanced3() {
        // insert in the beginning
        TextChain chain3 = new TextChain("дай ложку!");
        assertEquals(0, chain3.insert("Пожалуйста, по", 0));
        assertEquals(7, chain3.size());
        assertEquals("Пожалуйста, подай ложку!", chain3.toString());
        assertEquals(0, chain3.getChain().get(0).getPositionStart());
        assertEquals(10, chain3.getChain().get(0).getPositionEnd());
    }

    @Test
    public void testOperationsAdvanced4() {
        // insert in the end
        TextChain chain4 = new TextChain("Шум");
        assertEquals(0, chain4.insert("ный город", 4));
        assertEquals(3, chain4.size());
        assertEquals("Шумный город", chain4.toString());
        assertEquals(6, chain4.getChain().get(1).getPositionStart());
        assertEquals(7, chain4.getChain().get(1).getPositionEnd());
    }

    @Test
    public void testOperationsAdvanced5() {
        // remove of everything, then append
        TextChain chain5 = new TextChain("Что-то с чем-то!");
        assertEquals(0, chain5.remove(0, 16));
        assertEquals(0, chain5.size());
        assertNotNull(chain5.getChain());
        assertEquals("", chain5.toString());
        chain5.append("Вернули.");
        assertEquals(2, chain5.size());
    }

    @Test
    public void testOperationsAdvanced6() {
        // remove cutting words
        TextChain chain6 = new TextChain("Старый клён стучит в окно.");
        assertEquals(0, chain6.remove(1, 12));
        assertEquals(6, chain6.size());
        assertEquals("Стучит в окно.", chain6.toString());
        assertEquals(0, chain6.getChain().get(0).getPositionStart());
        assertEquals(6, chain6.getChain().get(0).getPositionEnd());
        assertEquals(9, chain6.getChain().get(4).getPositionStart());
        assertEquals(13, chain6.getChain().get(4).getPositionEnd());
    }

    private TextEntrySeparator sep(String p_in) {
        return new TextEntrySeparator(p_in, 0);
    }

    private TextEntryWord word(String p_in) {
        return new TextEntryWord(p_in, 0);
    }

    @Test
    public void testChainExtraReal() {
        // check various separators identity
        TextChain chain7 = new TextChain("где лучше — у незнакомца. крикнул: «По-ошла вон, треклятая!»");
        assertEquals(0, chain7.getChain().get(0).getDiffMeasure(word("где")));
        assertEquals(0, chain7.getChain().get(1).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(2).getDiffMeasure(word("лучше")));
        assertEquals(0, chain7.getChain().get(3).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(4).getDiffMeasure(sep("-")));
        assertEquals(0, chain7.getChain().get(5).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(6).getDiffMeasure(word("у")));
        assertEquals(0, chain7.getChain().get(7).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(8).getDiffMeasure(word("незнакомца")));
        assertEquals(0, chain7.getChain().get(9).getDiffMeasure(sep(".")));
        assertEquals(0, chain7.getChain().get(10).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(11).getDiffMeasure(word("крикнул")));
        assertEquals(0, chain7.getChain().get(12).getDiffMeasure(sep(":")));
        assertEquals(0, chain7.getChain().get(13).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(14).getDiffMeasure(sep("\"")));
        assertEquals(0, chain7.getChain().get(15).getDiffMeasure(word("По")));
        assertEquals(0, chain7.getChain().get(16).getDiffMeasure(sep("-")));
        assertEquals(0, chain7.getChain().get(17).getDiffMeasure(word("ошла")));
        assertEquals(0, chain7.getChain().get(18).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(19).getDiffMeasure(word("вон")));
        assertEquals(0, chain7.getChain().get(20).getDiffMeasure(sep(",")));
        assertEquals(0, chain7.getChain().get(21).getDiffMeasure(sep(" ")));
        assertEquals(0, chain7.getChain().get(22).getDiffMeasure(word("треклятая")));
        assertEquals(0, chain7.getChain().get(23).getDiffMeasure(sep("!")));
        assertEquals(0, chain7.getChain().get(24).getDiffMeasure(sep("\"")));
        assertEquals(25, chain7.getChain().size());
    }

}
