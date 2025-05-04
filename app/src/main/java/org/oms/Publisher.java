package org.oms;

import io.aeron.Aeron;
import io.aeron.Publication;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;
import java.math.BigDecimal;

import org.oms.OrderMessage;

public class Publisher {
    public static void run() {        

        try (Aeron aeron = Aeron.connect(new Aeron.Context())) {
            String CHANNEL = "aeron:ipc";
            int STREAM_ID = 1001;

            Publication publication = aeron.addPublication(CHANNEL, STREAM_ID);
            
            OrderMessage order = new OrderMessage("1", "AAPL", "BUY", new BigDecimal(100), new BigDecimal(100));
            byte[] orderBytes = order.toBytes();

            UnsafeBuffer buffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64));
            buffer.putBytes(0, orderBytes);

            long result = publication.offer(buffer, 0, orderBytes.length);
            if (result > 0) {
                System.out.println("✅ Published: " + order);
            } else {
                System.out.println("❌ Failed to publish. Result: " + result);
            }
        }
    }
}
