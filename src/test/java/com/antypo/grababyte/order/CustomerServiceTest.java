package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private MenuService mockMenuService;
    @Mock
    private OrderService mockOrderService;
    @InjectMocks
    private CustomerService customerService;  // CustomerService with mocks injected

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNoOrder() {
        // Mock scanner to be able to input own values for testing.
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(2);
        CustomerService.setScanner(mockScanner);
        String expectedOutput = """
                Hello and welcome to Grab a Byte! We offer up to petabytes of delicious lunches and streams of nourishing drinks.\s
                Would you like to make an order?
                 1. Yes    or    2. No
                Please select an option:\s
                
                Thank you for using our services! Hoping to work together in the future!
                
                Oh they're gone! Here is today's summary:
                Today's revenue is *DRUMROLL*: 0,00zł.
                Top selling items were:
                """;

        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        customerService.open();

        String capturedOutput = outputStream.toString();
        assertEquals(expectedOutput, capturedOutput);
        System.setOut(System.out);
    }

    @Test
    void testOrderLunch() {
        Scanner mockScanner = mock(Scanner.class);

        // Important parts to be contained in output
        String expectedIntro = "Hello and welcome to Grab a Byte! We offer up to petabytes of delicious lunches and streams of nourishing drinks.";
        String expectedIngredients = "The main course you've selected contains the following ingredients:\nHubert";
        String expectedAddedToOrderInfo = "Christmas Egg with Sernik has been added to your order! Your current total is: 13011,99zł.";
        String expectedOrderSummary = "1. 1x Christmas Egg with Sernik for 13011,99zł\n\nYour current total is: 13011,99zł.";
        String expectedDaySummary = """
                Oh they're gone! Here is today's summary:
                Today's revenue is *DRUMROLL*: 13011,99zł.
                Top selling items were:
                Christmas Egg - ordered 1 times
                Sernik - ordered 1 times""";

        // Mock the responses to make an order
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(3)
                .thenReturn(2);

        CustomerService.setScanner(mockScanner);

        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        customerService.open();

        String capturedOutput = outputStream.toString();

        assertTrue(capturedOutput.contains(expectedIntro));
        assertTrue(capturedOutput.contains(expectedIngredients));
        assertTrue(capturedOutput.contains(expectedAddedToOrderInfo));
        assertTrue(capturedOutput.contains(expectedOrderSummary));
        assertTrue(capturedOutput.contains(expectedDaySummary));

        System.setOut(System.out);
    }
}
