package org.oms;

import io.aeron.Aeron;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;

public class Subscriber {
    public static void run() {
        try (Aeron aeron = Aeron.connect()) {        
            String CHANNEL = "aeron:ipc";
            int STREAM_ID = 1001;

            Subscription subscription = aeron.addSubscription(CHANNEL, STREAM_ID);

            FragmentHandler handler = (buffer, offset, length, header) -> {
                byte[] data = new byte[length];
                buffer.getBytes(offset, data);
                System.out.println("📨 Received: " + new String(data));
            };

            System.out.println("📡 OMS Subscriber running...");
            while (true) {
                int fragments = subscription.poll(handler, 10);
                if (fragments == 0) Thread.yield();
            }
        }
    }
}
