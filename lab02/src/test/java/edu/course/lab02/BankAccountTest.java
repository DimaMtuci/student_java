package edu.course.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    @Test
    void testSuccessfulWithdraw() {
        BankAccount account = new BankAccount(1000);
        account.withdraw(400);
        assertEquals(600, account.getBalance());
    }

    @Test
    void testWithdrawMoreThanBalanceThrowsException() {
        BankAccount account = new BankAccount(500);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(600));
    }
}