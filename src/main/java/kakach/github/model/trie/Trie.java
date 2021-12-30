package kakach.github.model.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Trie<T> {

    private final TrieNode<T> root;

    private final Function<String, List<T>> keyGenerator;

    public Trie(Function<String, List<T>> keyGenerator) {
        this.root = new TrieNode<>(null);
        this.keyGenerator = keyGenerator;
    }

    public void addValue(String value) {
        List<T> keys = this.keyGenerator.apply(value);
        addValue(keys, value);
    }

    //添加一个值
    private void addValue(List<T> keys, String value) {
        if (null == keys || keys.isEmpty()) return;
        int keysSize = keys.size();
        int index = 0;
        TrieNode<T> currentNode = this.root;
        T currentKey;
        while (index<keysSize) {
            currentKey = keys.get(index);
            Optional<TrieNode<T>> childOpt = currentNode.getChild(currentKey);
            if (childOpt.isPresent()) {
                currentNode = childOpt.get();
                index++;
                continue;
            }
            TrieNode<T> newNode = new TrieNode<>(currentKey);
            currentNode.addChild(newNode);
            currentNode = newNode;
            index++;
        }
        currentNode.addValue(value);
    }

    public Optional<String> getValue(String v) {
        return getValue(this.keyGenerator.apply(v));
    }

    private Optional<String> getValue(List<T> keys) {
        if (keys.isEmpty() || !root.hasChild()) {
            return Optional.empty();
        }
        int keysSize = keys.size();
        int index = 0;
        TrieNode<T> currentNode = this.root;
        T currentKey;
        while (index<keysSize) {
            currentKey = keys.get(index);
            Optional<TrieNode<T>> childOpt = currentNode.getChild(currentKey);
            if (childOpt.isPresent()) {
                currentNode = childOpt.get();
                index++;
                continue;
            }
            break;
        }
        if (currentNode.getValueFlag()) {
            return Optional.of(currentNode.getValues().get(0));
        }
        return Optional.empty();
    }

    public List<String> getValuesByPrefix(String v) {
        return getSamePrefixValues(this.keyGenerator.apply(v));
    }

    private List<String> getSamePrefixValues(List<T> keys) {
        List<String> res = new ArrayList<>();
        if (keys.isEmpty() || !root.hasChild()) {
            return res;
        }
        int keysSize = keys.size();
        int index = 0;
        TrieNode<T> currentNode = this.root;
        T currentKey;
        //与前缀匹配，深度优先
        while (index<keysSize) {
            currentKey = keys.get(index);
            Optional<TrieNode<T>> childOpt = currentNode.getChild(currentKey);
            if (childOpt.isPresent()) {
                currentNode = childOpt.get();
                index++;
                continue;
            }
            break;
        }
        if (currentNode.getValueFlag()) {
            res.addAll(currentNode.getValues());
        }
        //宽度优先遍历
        List<TrieNode<T>> children = currentNode.getChildren();
        while (!children.isEmpty()) {
            children.stream()
                    .filter(TrieNode::getValueFlag)
                    .forEach(node -> res.addAll(node.getValues()));
            Optional<ArrayList<TrieNode<T>>> childrenOpt = children.stream()
                    .filter(TrieNode::hasChild)
                    .map(TrieNode::getChildren)
                    .reduce((a,b) -> {
                        b.addAll(a);
                        return b;
                    });
            if (childrenOpt.isPresent()) {
                children = childrenOpt.get();
                continue;
            }
            break;
        }
        return res;
    }

}

