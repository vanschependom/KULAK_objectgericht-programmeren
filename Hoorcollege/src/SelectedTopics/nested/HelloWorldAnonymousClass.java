package SelectedTopics.nested;

public class HelloWorldAnonymousClass {

    interface HelloWorld {
        public void greet();
        public void greetSomeone(String someone);
    }

    public void sayHello() {
        // Example classical local class
        class EnglishGreeting implements HelloWorld {
            String name;
            @Override
            public void greet() {
                greetSomeone("world");
            }
            @Override
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hello " + name);
            }

            public void printHi() {
                System.out.println("Hi");
            }
        }

        HelloWorld englishGreeting = new EnglishGreeting();
        //EnglishGreeting englishGreeting = new EnglishGreeting();
        englishGreeting.greet();
        //englishGreeting.printHi();

        //Anonymous class example
        HelloWorld frenchGreeting = new HelloWorld() {
            String name;
            @Override
            public void greet() {
                greetSomeone("tout le monde");
            }
            @Override
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Salut " + name);
            }
        };

        frenchGreeting.greetSomeone("Fred");

    }

    public static void main(String[] args) {
        HelloWorldAnonymousClass myApp =  new HelloWorldAnonymousClass();
        myApp.sayHello();
    }

}
