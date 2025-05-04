package org.oms;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;

public class OrderMessage {
    public String orderId;
    public String symbol;
    public String side;
    public BigDecimal quantity;
    public BigDecimal price;  
    
    public OrderMessage(String orderId, String symbol, String side, BigDecimal quantity, BigDecimal price) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
    }

    public byte[] toBytes() {
        byte[] orderIdBytes = orderId.getBytes(StandardCharsets.UTF_8);
        byte[] symbolBytes = symbol.getBytes(StandardCharsets.UTF_8);
        byte[] sideBytes = side.getBytes(StandardCharsets.UTF_8);
        byte[] quantityBytes = quantity.toPlainString().getBytes(StandardCharsets.UTF_8);
        byte[] priceBytes = price.toPlainString().getBytes(StandardCharsets.UTF_8);

        int totalLength = 20 + // 5 integers for lengths
                         orderIdBytes.length + symbolBytes.length + sideBytes.length + 
                         quantityBytes.length + priceBytes.length;

        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        
        // Write lengths and data
        buffer.putInt(orderIdBytes.length);
        buffer.put(orderIdBytes);
        
        buffer.putInt(symbolBytes.length);
        buffer.put(symbolBytes);
        
        buffer.putInt(sideBytes.length);
        buffer.put(sideBytes);
        
        buffer.putInt(quantityBytes.length);
        buffer.put(quantityBytes);
        
        buffer.putInt(priceBytes.length);
        buffer.put(priceBytes);

        return buffer.array();
    }

    public static OrderMessage fromBytes(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        
        // Read orderId
        int orderIdLength = buffer.getInt();
        byte[] orderIdBytes = new byte[orderIdLength];
        buffer.get(orderIdBytes);
        String orderId = new String(orderIdBytes, StandardCharsets.UTF_8);
        
        // Read symbol
        int symbolLength = buffer.getInt();
        byte[] symbolBytes = new byte[symbolLength];
        buffer.get(symbolBytes);
        String symbol = new String(symbolBytes, StandardCharsets.UTF_8);
        
        // Read side
        int sideLength = buffer.getInt();
        byte[] sideBytes = new byte[sideLength];
        buffer.get(sideBytes);
        String side = new String(sideBytes, StandardCharsets.UTF_8);
        
        // Read quantity
        int quantityLength = buffer.getInt();
        byte[] quantityBytes = new byte[quantityLength];
        buffer.get(quantityBytes);
        BigDecimal quantity = new BigDecimal(new String(quantityBytes, StandardCharsets.UTF_8));
        
        // Read price
        int priceLength = buffer.getInt();
        byte[] priceBytes = new byte[priceLength];
        buffer.get(priceBytes);
        BigDecimal price = new BigDecimal(new String(priceBytes, StandardCharsets.UTF_8));
        
        return new OrderMessage(orderId, symbol, side, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
            "orderId='" + orderId + '\'' +
            ", symbol='" + symbol + '\'' +
            ", side='" + side + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            '}';
    }
}