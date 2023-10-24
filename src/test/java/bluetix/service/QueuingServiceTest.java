package bluetix.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class QueuingServiceTest {
    @Test
    void testDequeue() {

    }

    @Test
    void testDrainQueue() {

    }

    @Test
    void testEnqueue() {
        QueuingService<Integer> queue = new QueuingService<>(4);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(false, queue.inService(2));
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (Exception e) {
            // TODO: handle exception
        }
        assertEquals(true, queue.inService(2));
    }

    @Test
    void testGetPosition() {

    }

    @Test
    void testInQueueOrService() {

    }

    @Test
    void testInService() {

    }

    @Test
    void testIsEmpty() {

    }

    @Test
    void testMoveToService() {

    }

    @Test
    void testRemoveFromService() {

    }
}
