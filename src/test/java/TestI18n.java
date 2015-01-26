import org.akrasnyansky.typetest.i18n.I18nHelper;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestI18n {

    @Test
    public void testResourceBundleInit() {
        assertNotNull(I18nHelper.message("main.window.title"));
        assertEquals("Typing Test", I18nHelper.message("main.window.title"));
    }

    @Test
    public void testUTF8String() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Русский", I18nHelper.message("buttonToggleLanguage.text"));
    }

}
