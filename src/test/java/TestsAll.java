import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestTextSampleRepository.class,
    TestCompareHelper.class,
    TestTextEntryWord.class,
    TestTextEntrySeparator.class,
    TestTextChain.class,
    TestTypingFlowMatcher.class,
    TestI18n.class
})
public class TestsAll {
}
