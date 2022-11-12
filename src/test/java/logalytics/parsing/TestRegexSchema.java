package logalytics.parsing;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRegexSchema {
    @Test
    public void testConstruction() {
        String patternStr = "[a-z]/[A-Z]";
        String group1 = "a";
        String group2 = "b";
        List<String> groups = Lists.newArrayList(group1, group2);
        RegexSchema logSchema = new RegexSchema(patternStr, groups);
        assertThat(logSchema.numGroups()).isEqualTo(2);
        assertThat(logSchema.group(0)).isEqualTo(group1);
        assertThat(logSchema.group(1)).isEqualTo(group2);
    }

    @Test
    public void testMatch() {
        String patternStr = "[a-z]/[A-Z]";
        List<String> groups = Lists.newArrayList("a", "b");
        RegexSchema logSchema = new RegexSchema(patternStr, groups);
        assertThat(logSchema.matcher("a/B").pattern().pattern()).isEqualTo(patternStr);
        assertThat(logSchema.matcher("a/B").matches()).isTrue();
    }
}
