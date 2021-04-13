package request;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;

public class ClientTCP {

  private static void handleAnswer(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload =
            new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    DataInputStream src = new DataInputStream(payload);
    byte length = src.readByte();
    short id = src.readShort();
    byte errorCode = src.readByte();
    int answer = src.readInt();
    byte check = src.readByte();
    System.out.println("len: " + length);
    System.out.println("id: " + id);
    System.out.println("err: " + errorCode);
    System.out.println("ans: " + answer);
    System.out.println("sum: " + check);

    System.out.println("Answer for request ID number " + id + ": " + answer);

  }

  public static void main(String args[]) throws Exception {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
    int destPort = Integer.parseInt(args[1]);               // Destination port

    Socket sock = new Socket(destAddr, destPort);

    Request request = new Request((short) (Math.random()*100), 5, 1, 0, 6, 12);

    System.out.println("Display friend");
    System.out.println(request + " ID:" + request.ID); // Display friend just to check what we send


    RequestEncoder encoder = new RequestEncoderBin();

    System.out.println("Sending Friend (Binary)");
    OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
    out.write(encoder.encode(request)); // Encode and send

    System.out.println("Awaiting operation");
    ResponseDecoderBin decoder = new ResponseDecoderBin();
    Response res = decoder.decode(sock.getInputStream());

    System.out.println(res);

    sock.close();

  }
}
