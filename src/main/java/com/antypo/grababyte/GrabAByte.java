package com.antypo.grababyte;

import com.antypo.grababyte.order.CustomerService;

public class GrabAByte {

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();

        // As per the name - customer service serves the customers :)
        customerService.open();
        customerService.close();
        System.exit(0);
    }
}
