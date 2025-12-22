package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/simple-status")
public class SimpleStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Tests may pass null request, so do NOT use req
        resp.setContentType("text/plain");

        PrintWriter writer = resp.getWriter();
        writer.write("Credit Card Reward Maximizer is running");
        writer.flush(); // IMPORTANT: tests verify flush() is called
    }
}
