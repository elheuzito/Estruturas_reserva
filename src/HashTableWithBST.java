import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class HashTableWithBST {
    private BinarySearchTree[] buckets;
    private final int SIZE = 26;

    // CADA BUCKET DA HASHTABLE VAI TER UMA ARVOREBINARIA DE BUSCA
    public HashTableWithBST() {
        buckets = new BinarySearchTree[SIZE];
        for (int i = 0; i < SIZE; i++) {
            buckets[i] = new BinarySearchTree();
        }
    }

    private int getBucketIndex(String word) {
        return word.toLowerCase().charAt(0) - 'a';
    }

    public void insert(String word, int index) {
        int bucketIndex = getBucketIndex(word);
        buckets[bucketIndex].insert(word, index);
    }

    public List<Integer> search(String word) {
        int bucketIndex = getBucketIndex(word);
        return buckets[bucketIndex].search(word);
    }

    public void delete(String word) {
        int bucketIndex = getBucketIndex(word);
        buckets[bucketIndex].delete(word);
    }

    public void printHashTable() {
        for (int i = 0; i < SIZE; i++) {
            if (!buckets[i].isEmpty()) {
                buckets[i].printInOrder();
            }
        }
    }
    public void readKeywordsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    insert(word, -1); // Índice -1 usado para diferenciar as palavras-chave
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void buildIndexFromTextFile(String textFilename, String keywordsFilename) {
        // Primeiro, ler as palavras-chave do arquivo keywordsFilename
        readKeywordsFromFile(keywordsFilename);

        // Segundo, ler o arquivo de texto e registrar as linhas em que cada palavra-chave aparece
        try (BufferedReader br = new BufferedReader(new FileReader(textFilename))) {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Remover pontuações no final das palavras
                    word = word.replaceAll("[^a-zA-Z0-9]+$", "");
                    if (!word.isEmpty() && search(word) != null) {
                        insert(word, lineNumber);
                    }
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}