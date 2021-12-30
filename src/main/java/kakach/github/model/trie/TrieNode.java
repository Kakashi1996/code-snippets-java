package kakach.github.model.trie;

import java.util.*;

public class TrieNode<T> {

    private final T key;

    private Set<String> values;

    private boolean valueFlag;

    private final ArrayList<TrieNode<T>> children;

    public TrieNode(T key) {
        this.key = key;
        this.valueFlag = false;
        this.children = new ArrayList<>();
    }

    public T getKey() {
        return key;
    }

    public void addChild(TrieNode<T> node) {
        this.children.add(node);
    }

    public Optional<TrieNode<T>> getChild(T key) {
        return this.children.stream()
                .filter(e -> e.getKey().equals(key))
                .findFirst();
    }

    public boolean hasChild() {
        return !children.isEmpty();
    }

    public boolean hasChild(T key) {
        return this.children.stream()
                .anyMatch(e -> e.getKey().equals(key));
    }

    public boolean getValueFlag() {
        return valueFlag;
    }

    public void setValueFlag(boolean flag) {
        this.valueFlag = flag;
    }

    public synchronized void addValue(String value) {
        if (!this.valueFlag) {
            setValueFlag(true);
            values = new HashSet<>();
        }
        values.add(value);
    }

    public List<String> getValues() {
        if (!this.valueFlag) return new ArrayList<>();
        return new ArrayList<>(values);
    }

    public ArrayList<TrieNode<T>> getChildren() {
        return this.children;
    }

}
