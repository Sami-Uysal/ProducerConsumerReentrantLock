
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProducerConsumer {
    Random random  = new Random();
    ReentrantLock lock  = new ReentrantLock();
    Condition cond = lock.newCondition();
    Queue<Integer> queue = new LinkedList<Integer>();
    private int limit = 10;
    
    
    public void produce() {
        while (true) {
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
            lock.lock();
            
                
                if (queue.size() == limit) {
                    
                    try {
                        cond.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Integer value = random.nextInt(100);
                queue.offer(value);
                System.out.println("Producer Üretiyor : " + value);
                cond.signal();
                
            lock.unlock();
      
        }
        
        
    }
    public void consume() {
        
        while (true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            lock.lock();
                
                if (queue.size() == 0) {
                    
                    try {
                        cond.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Integer value =  queue.poll();
                System.out.println("Consumer Tüketiyor : " + value);
                System.out.println("Queue Size : " + queue.size());
                cond.signal();
                
                
            lock.unlock();
            
        }
        
    }
    
}
