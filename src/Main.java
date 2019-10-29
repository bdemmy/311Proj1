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
            rest.tick();

            if (i <= 2 || i == 20){
                System.out.printf("Tick #: %d\n", i);
                System.out.println(rest);
            } else if (i == 3) {
                System.out.println("...");
                System.out.println("");
            }
        }
    }

    public static void main(String[] args) {
        Restaurant testaraunt = new Restaurant(4, 2.f, 1, 3);

        System.out.printf("%d servers, %.2f demand\n", 4, 2.f);
        runRestaraunt(testaraunt);

    }
}

/*
EXAMPLE OUTPUT:

4 servers, 2.00 demand
Tick #: 1
   4 Customers in service
   0 Customers completed service
   0 Customers in queue
   Total wait time: 0
   Wait time: min: 0, max: 0, avg: 0.00

Tick #: 2
   4 Customers in service
   0 Customers completed service
   3 Customers in queue
   Total wait time: 0
   Wait time: min: 0, max: 0, avg: 0.00

...

Tick #: 20
   4 Customers in service
   26 Customers completed service
   7 Customers in queue
   Total wait time: 129
   Wait time: min: 0, max: 18, avg: 3.49
 */