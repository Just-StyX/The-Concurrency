/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.example.chapter2.ConcurrentServer;
import org.example.chapter2.LoadCsvCommand;
import org.example.chapter2.ReportCommand;
import org.example.chapter2.SerialServer;

import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) {
//        System.out.println(
//                LoadCsvCommand
//                        .getInstance("/home/lu/Documents/Workspace/concurrency/app/src/main/resources/data.csv", StandardCharsets.UTF_8, ",").execute()
//        );
//        System.out.println(ReportCommand.getInstance("alb;population;2002", ";").execute());

        ConcurrentServer.startServer();
    }
}
