package org.oms;

import io.aeron.Aeron;
import io.aeron.Publication;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;

public class Publisher {
    public static void run() {        

        try (Aeron aeron = Aeron.connect(new Aeron.Context())) {
            String CHANNEL = "aeron:ipc";
            int STREAM_ID = 1001;

            Publication publication = aeron.addPublication(CHANNEL, STREAM_ID);
            String message = "Hello from OMS Publisher!";
            UnsafeBuffer buffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64));
            buffer.putBytes(0, message.getBytes());

            long result = publication.offer(buffer, 0, message.length());
            if (result > 0) {
                System.out.println("✅ Published: " + message);
            } else {
                System.out.println("❌ Failed to publish. Result: " + result);
            }
        }
    }
}
