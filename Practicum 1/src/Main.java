import be.kuleuven.cs.som.annotate.*;

public class Main {

    public static void main(String[] args) {
        File bestand = new File("test.txt", 100, false);
        bestand.changeName("veranderd.txt");
    }

}
