package org.oms;

public class OmsMain {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: run [publisher|subscriber]");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "publisher":
                Publisher.run();
                break;
            case "subscriber":
                Subscriber.run();
                break;
            default:
                System.out.println("Unknown mode: " + args[0]);
        }
    }
}
