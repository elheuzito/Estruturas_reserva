// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        HashTableWithBST hashTable = new HashTableWithBST();
        hashTable.buildIndexFromTextFile("src/text.txt", "src/keywords.txt");

        // Imprimir a tabela hash e os nós das BSTs
        hashTable.printHashTable();
        }
    }


