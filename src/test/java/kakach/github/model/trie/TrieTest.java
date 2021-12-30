package kakach.github.model.trie;

import kakach.github.util.KeyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrieTest {

    @Test
    public void testWriteRead() {
        Trie<Character> vocabularies = new Trie<>(KeyGenerator::string2CharacterList);
        vocabularies.addValue("hello");
        vocabularies.addValue("hell");
        vocabularies.addValue("hellen");
        vocabularies.addValue("world");
        vocabularies.addValue("hasaki");
        Assertions.assertEquals("hello", vocabularies.getValue("hello").orElse(""));
        Assertions.assertEquals(3,vocabularies.getValuesByPrefix("he").size());
    }

}
