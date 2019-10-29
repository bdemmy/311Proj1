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
    private Customer customer;

    /**
     * @return Returns if the customer has finished service.
     */
    public boolean updateGuest() {
        if (customer == null) {
            return false;
        }

        if (customer.getServiceTime() <= 0) {
            customer = null;
            return true;
        }

        if (customer != null) {
            customer.tickServiceTime();
        }

        return false;
    }

    public void assignCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isIdle() {
        return customer == null;
    }
}

public class Restaraunt {
    private float poi_mean;
    private int min_service;
    private int max_service;
    private int completed;

    private Random random;

    private List<Server> servers;
    private List<Integer> wait_times;
    private Deque<Customer> wait_queue;

    public Restaraunt(int servers, float mean, int min_service, int max_service) {
        this.poi_mean = mean;
        this.min_service = min_service;
        this.max_service = max_service;
        this.wait_queue = new ArrayDeque<>();

        this.random = new Random(97);

        this.servers = new ArrayList<>();
        for (int i = 0; i < servers; i++) {
            this.servers.add(new Server());
        }

        this.wait_times = new ArrayList<>();
    }

    public void tick() {
        for (Server server : servers) {
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

        int min = 0, max = 0, sum = 0;

        if (!wait_queue.isEmpty()) {
            min = Collections.min(wait_queue, Comparator.comparing(Customer::getWaitTime)).getWaitTime();
            max = Collections.max(wait_queue, Comparator.comparing(Customer::getWaitTime)).getWaitTime();
        }

        if (!wait_times.isEmpty()) {
            min = Math.min(min, Collections.min(wait_times));
            max = Math.max(max, Collections.max(wait_times));
        }

        for (Customer cust : wait_queue) {
            sum += cust.getWaitTime();
        }

        for (int time : wait_times) {
            sum += time;
        }

        return String.format("   %d Customers in service\n" +
                        "   %d Customers completed service\n" +
                        "   %d Customers in queue\n" +
                        "   Total wait time: %d\n" +
                        "   Wait time: min: %d, max: %d, avg: %.2f\n",
                in_service, completed,
                wait_queue.size(),
                sum, min, max, (float) sum / wait_queue.size()
        );
    }

    private void updateWaitList() {
        for (Customer customer : wait_queue) {
            customer.tickWaitTime();
        }
    }

    private void checkArrivals() {
        for (int i = 0; i < getPoissonRandom(this.poi_mean); i++) {
            wait_queue.push(
                    new Customer(min_service + random.nextInt(max_service))
            );
        }
    }

    private void assignCustomers() {
        for (Server serv : servers) {
            if (serv.isIdle() && !wait_queue.isEmpty()) {
                Customer customer = wait_queue.pop();

                wait_times.add(customer.getWaitTime());

                serv.assignCustomer(customer);
            }
        }
    }

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

    private int getCustomersInService() {
        int in_service = 0;
        for (Server server : servers) {
            if (!server.isIdle()) {
                in_service += 1;
            }
        }

        return in_service;
    }
}
