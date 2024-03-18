package SelectedTopics.nested;

public class AnonymousClassBook {

    public abstract class Book {

        //Must be protected else not accessible from subclasses
        protected String title;

        public Book(String title) {
            this.title = title;
        }

        public abstract void printBookTitle();

    }

    public void makeAnonymousBook() {
        Book myBook = new Book("OGP is fun!") {
            @Override
            public void printBookTitle() {
                System.out.println("Book title is " + title);
            }
        };

        myBook.printBookTitle();
    }

    public static void main(String[] args) {
        AnonymousClassBook anonymousClassBook = new AnonymousClassBook();
        anonymousClassBook.makeAnonymousBook();

    }

}
