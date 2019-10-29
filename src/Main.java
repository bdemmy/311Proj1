public class Main {
    static final int TICKS_PER_MINUTE = 1;

    public static void main(String[] args) {
        Restaraunt testaraunt = new Restaraunt(8, 2.f, 1, 12);
        Restaraunt testaraunt1 = new Restaraunt(4, 2.f, 1, 12);
        Restaraunt testaraunt2 = new Restaraunt(2, 2.f, 1, 12);
        Restaraunt testaraunt3 = new Restaraunt(1, 2.f, 1, 12);


        Restaraunt testaraunt4 = new Restaraunt(8, .25f, 1, 3);
        Restaraunt testaraunt5 = new Restaraunt(4, .25f, 1, 3);
        Restaraunt testaraunt6 = new Restaraunt(2, .25f, 1, 3);
        Restaraunt testaraunt7 = new Restaraunt(1, .25f, 1, 3);

        for (int i = 1; i <= 20 * TICKS_PER_MINUTE; i++) {
            System.out.printf("Tick #: %d\n", i);

            testaraunt.tick();
            testaraunt1.tick();
            testaraunt2.tick();
            testaraunt3.tick();

            testaraunt4.tick();
            testaraunt5.tick();
            testaraunt6.tick();
            testaraunt7.tick();
        }

        System.out.printf("%d servers, %.2f demand\n", 8, 2.f);
        System.out.println(testaraunt);
        System.out.printf("%d servers, %.2f demand\n", 4, 2.f);
        System.out.println(testaraunt1);
        System.out.printf("%d servers, %.2f demand\n", 2, 2.f);
        System.out.println(testaraunt2);
        System.out.printf("%d servers, %.2f demand\n", 1, 2.f);
        System.out.println(testaraunt3);

        System.out.printf("%d servers, %.2f demand\n", 8, .25f);
        System.out.println(testaraunt4);
        System.out.printf("%d servers, %.2f demand\n", 4, .25f);
        System.out.println(testaraunt5);
        System.out.printf("%d servers, %.2f demand\n", 2, .25f);
        System.out.println(testaraunt6);
        System.out.printf("%d servers, %.2f demand\n", 1, .25f);
        System.out.println(testaraunt7);
    }
}
