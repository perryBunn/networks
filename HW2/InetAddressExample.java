import java.io.IOException;
import java.net.*; // for InetAddress
import java.util.Arrays;
import java.util.Scanner;

public class InetAddressExample {
    public static void main(String[] args) {
        // Get name and IP address of the local host
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Local Host:");
            System.out.println("\t" + address.getHostName());
            System.out.println("\t" + address.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Unable to determine this host's address");
        }
        if (args.length == 0) {
            System.out.print("Please enter a host name: ");
            Scanner scan = new Scanner(System.in);
            String res = scan.nextLine();
            try {
                InetAddress address = InetAddress.getByName(res);
                System.out.println("\t" + address.getHostName());
                System.out.println("\t" + address.getHostAddress());
                System.out.println("\t" + Arrays.toString(address.getHostAddress().getBytes()));

            } catch (UnknownHostException e) {
                System.out.println("Unable to find address for " + res);
            }
        } else {
            for (int i = 0; i < args.length; i++) {
                // Get name(s)/address(es) o hosts given on command-line
                try {
                    InetAddress[] addressList = InetAddress.getAllByName(args[i]);
                    System.out.println(args[i] + ":");
                    // Print the first name. Assume array contains at least one entry.
                    System.out.println("\t" + addressList[0].getHostName());
                    for (int j = 0; j < addressList.length; j++) {
                        System.out.println("\t" + addressList[i].getHostAddress());
                    }
                } catch (UnknownHostException e) {
                    System.out.println("Unable to find address for " + args[i]);
                }
            }
        }
    }
}
