/*
Brendon Demmy
E01723637

COSC 311 - FA19
Project 1
 */

import java.util.*;

class Customer {
    private int wait_time;
    private int service_time;

    public Customer(int service_time) {
        this.service_time = service_time;
    }

    public void tickServiceTime() {
        this.service_time -= 1;
    }

    public void tickWaitTime() {
        wait_time += 1;
    }

    public int getServiceTime() {
        return this.service_time;
    }

    public int getWaitTime() {
        return wait_time;
    }
}

class Server {
    // Maintain a handle to the customer that the server is dealing with
    private Customer customer;

    // Checks to see if the customer has finished service.
    // If not, tick the customer's wait time and continue.
    // It returns true if a customer leaves on this tick.
    public boolean updateGuest() {
        if (customer == null) {
            return false;
        }

        // The customer is ready to leave, so lets set them to null
        if (customer.getServiceTime() <= 0) {
            customer = null;
            return true;
        }

        if (customer != null) {
            customer.tickServiceTime();
        }

        return false;
    }

    // A setter for the customer handle
    public void assignCustomer(Customer customer) {
        this.customer = customer;
    }

    // Is the server idle?
    public boolean isIdle() {
        return customer == null;
    }
}

public class Restaurant {
    // Primitives
    private float poi_mean;
    private int min_service;
    private int max_service;
    private int completed;

    // Classes
    private Random random;

    // Collections (still classes)
    private List<Server> servers;
    private List<Integer> wait_times;
    private Deque<Customer> wait_queue;

    // Initialize the restaraunt object with the number of servers, the poisson mean,
    //  the minimum service time for customers, as well as the maximum service time.
    public Restaurant(int servers, float mean, int min_service, int max_service) {
        this.poi_mean = mean;
        this.min_service = min_service;
        this.max_service = max_service;
        this.wait_queue = new ArrayDeque<>();

        // Seed with 97 as per project requirements
        this.random = new Random(97);

        // Our restaraunt server's list needs to be initialized
        this.servers = new ArrayList<>();
        for (int i = 0; i < servers; i++) {
            this.servers.add(new Server());
        }

        this.wait_times = new ArrayList<>();
    }

    // Called every tick that you want to update the restaraunt
    // Performs the correct actions in the order specified by the handout.
    public void tick() {
        for (Server server : servers) {
            // If the customer got released by the server, increase our completed counter
            if (server.updateGuest()) {
                completed += 1;
            }
        }

        updateWaitList();

        checkArrivals();

        assignCustomers();
    }

    @Override
    public String toString() {
        int in_service = getCustomersInService();

        int min = getMinWait();
        int max = getMaxWait();
        int sum = getWaitTotal();
        float avg = getAvgWait();

        // Now just do some ugly formatting to get the printing to look as specified
        return String.format("   %d Customers in service\n" +
                        "   %d Customers completed service\n" +
                        "   %d Customers in queue\n" +
                        "   Total wait time: %d\n" +
                        "   Wait time: min: %d, max: %d, avg: %.2f\n",
                in_service, completed,
                wait_queue.size(),
                sum, min, max, avg
        );
    }

    // Just tick every customer's wait time in the queue
    private void updateWaitList() {
        for (Customer customer : wait_queue) {
            customer.tickWaitTime();
        }
    }

    // Check for new arrivals as per the poisson distribution
    private void checkArrivals() {
        // We need to store the amount instead of just doing it in the for statement as the value will not be
        // cached in the for statement.
        int amount = getPoissonRandom(this.poi_mean);
        for (int i = 0; i < amount; i++) {
            wait_queue.push(
                    new Customer(min_service + random.nextInt(max_service))
            );
        }
    }

    // Assign customers to servers that are idle, and then append how long they waited to the list of stored times
    private void assignCustomers() {
        for (Server serv : servers) {
            if (serv.isIdle() && !wait_queue.isEmpty()) {
                Customer customer = wait_queue.pop();

                wait_times.add(customer.getWaitTime());

                serv.assignCustomer(customer);
            }
        }
    }

    // The poisson dist function given by the handout
    private int getPoissonRandom(double mean) {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;

        do {
            p = p * random.nextDouble();
            k++;
        } while (p > L);

        return k - 1;
    }

    // This returns how many customers are currently in service. Very simple little method
    private int getCustomersInService() {
        int in_service = 0;
        for (Server server : servers) {
            if (!server.isIdle()) {
                in_service += 1;
            }
        }

        return in_service;
    }

    private int getMinWait() {
        int min = 0;

        if (!wait_queue.isEmpty()) {
            min = Collections.min(wait_queue, Comparator.comparing(Customer::getWaitTime)).getWaitTime();
        }

        // Now compare those min/max values with our completed customers' wait times
        if (!wait_times.isEmpty()) {
            min = Math.min(min, Collections.min(wait_times));
        }

        return min;
    }

    private int getMaxWait() {
        int max = 0;

        if (!wait_queue.isEmpty()) {
            max = Collections.max(wait_queue, Comparator.comparing(Customer::getWaitTime)).getWaitTime();
        }

        // Now compare those min/max values with our completed customers' wait times
        if (!wait_times.isEmpty()) {
            max = Math.max(max, Collections.max(wait_times));
        }

        return max;
    }

    private int getWaitTotal() {
        int sum = 0;

        // Now get the sum of the wait time
        for (Customer cust : wait_queue) {
            sum += cust.getWaitTime();
        }

        for (int time : wait_times) {
            sum += time;
        }

        return sum;
    }

    private float getAvgWait() {
        float avg = 0;
        int sum = getWaitTotal();

        if (wait_queue.size() > 0 || wait_times.size() > 0) {
            avg = sum / (float)(wait_queue.size() + wait_times.size());
        }
    }
}
