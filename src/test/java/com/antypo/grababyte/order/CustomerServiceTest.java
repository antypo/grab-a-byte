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

/**
 * Tests for customer service. It is lacking some coverage, but I wanted to show the general idea.
 * It's possible to mock going through all of the methods.
 */
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

        String expectedOutput = "Hello and welcome to Grab a Byte! We offer up to petabytes of delicious lunches and " +
                "streams of nourishing drinks. " + System.lineSeparator() +
                "Would you like to make an order?" + System.lineSeparator() +
                " 1. Yes    or    2. No" + System.lineSeparator() +
                "Please select an option: " + System.lineSeparator() +
                System.lineSeparator() +
                "Thank you for using our services! Hoping to work together in the future!" + System.lineSeparator() +
                System.lineSeparator() +
                "Oh they're gone! Here is today's summary:" + System.lineSeparator() +
                "Today's revenue is *DRUMROLL*: 0,00zł." + System.lineSeparator() +
                "Top selling items were:" + System.lineSeparator();


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
        String expectedIngredients = "The main course you've selected contains the following ingredients:" + System.lineSeparator() + "Hubert";
        String expectedAddedToOrderInfo = "Christmas Egg with Sernik has been added to your order! Your current total is: 13011,99zł.";
        String expectedOrderSummary = "1. 1x Christmas Egg with Sernik for 13011,99zł" + System.lineSeparator() + System.lineSeparator() +
                "Your current total is: 13011,99zł.";
        String expectedDaySummary = "Oh they're gone! Here is today's summary:" + System.lineSeparator() +
                "Today's revenue is *DRUMROLL*: 13011,99zł." + System.lineSeparator() +
                "Top selling items were:" + System.lineSeparator() +
                "Christmas Egg - ordered 1 times" + System.lineSeparator() +
                "Sernik - ordered 1 times";


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
