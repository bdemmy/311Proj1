/*
Brendon Demmy
E01723637

COSC 311 - FA19
Project 1
 */

public class Main {
    static final int TICKS_PER_MINUTE = 1;

    static void runRestaraunt(Restaurant rest) {
        for (int i = 1; i <= 20 * TICKS_PER_MINUTE; i++) {
            //System.out.printf("Tick #: %d\n", i);

            rest.tick();

            //System.out.println(rest);
        }
    }

    public static void main(String[] args) {
        Restaurant testaraunt = new Restaurant(8, 2.f, 1, 12);
        Restaurant testaraunt1 = new Restaurant(4, 2.f, 1, 12);
        Restaurant testaraunt2 = new Restaurant(2, 2.f, 1, 12);
        Restaurant testaraunt3 = new Restaurant(1, 2.f, 1, 12);

        Restaurant testaraunt4 = new Restaurant(8, .25f, 1, 3);
        Restaurant testaraunt5 = new Restaurant(4, .25f, 1, 3);
        Restaurant testaraunt6 = new Restaurant(2, .25f, 1, 3);
        Restaurant testaraunt7 = new Restaurant(1, .25f, 1, 3);


        System.out.printf("%d servers, %.2f demand\n", 8, 2.f);
        runRestaraunt(testaraunt);
        System.out.println(testaraunt);

        System.out.printf("%d servers, %.2f demand\n", 4, 2.f);
        runRestaraunt(testaraunt1);
        System.out.println(testaraunt1);

        System.out.printf("%d servers, %.2f demand\n", 2, 2.f);
        runRestaraunt(testaraunt2);
        System.out.println(testaraunt2);

        System.out.printf("%d servers, %.2f demand\n", 1, 2.f);
        runRestaraunt(testaraunt3);
        System.out.println(testaraunt3);

        System.out.printf("%d servers, %.2f demand\n", 8, .25f);
        runRestaraunt(testaraunt4);
        System.out.println(testaraunt4);

        System.out.printf("%d servers, %.2f demand\n", 4, .25f);
        runRestaraunt(testaraunt5);
        System.out.println(testaraunt5);

        System.out.printf("%d servers, %.2f demand\n", 2, .25f);
        runRestaraunt(testaraunt6);
        System.out.println(testaraunt6);

        System.out.printf("%d servers, %.2f demand\n", 1, .25f);
        runRestaraunt(testaraunt7);
        System.out.println(testaraunt7);
    }
}
