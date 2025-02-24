package org.example.chapter2;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientServerConnect {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8189)){
            var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        }
    }
}
