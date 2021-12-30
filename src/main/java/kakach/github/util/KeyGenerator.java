package kakach.github.util;

import java.util.List;
import java.util.stream.Collectors;

public class KeyGenerator {

    public static List<Character> string2CharacterList(String s) {
        return s.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
    }

}
