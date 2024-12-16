package com.antypo.grababyte;

import com.antypo.grababyte.order.CustomerService;

public class GrabAByte {

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();

        customerService.open();
        customerService.close();
    }
}
