import java.util.LinkedList;

class Buffer {
    private LinkedList<Integer> buffer = new LinkedList<>();
    private int capacity = 3;

    synchronized void produce(int item)
            throws InterruptedException {
        while (buffer.size() == capacity) {
            wait();
        }

        buffer.add(item);
        System.out.println("Produced: " + item);
        notifyAll();
    }

    synchronized void consume()
            throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }

        int item = buffer.removeFirst();
        System.out.println("Consumed: " + item);
        notifyAll();
    }
}

public class Main {
    public static void main(String[] args) {
        Buffer b = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++)
                    b.produce(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++)
                    b.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}