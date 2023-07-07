import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    void addition() {
        // 동질성 보존의 원칙
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(5), five.times(1));
    }

    @Test
    @DisplayName("$5 x 2 = $10")
    void testMultiplication() {
        Money fiveUSD = Money.dollar(5);
        assertEquals(Money.dollar(10), fiveUSD.times(2));
        assertEquals(Money.dollar(15), fiveUSD.times(3));

        Money fiveCHF = Money.franc(5);
        assertEquals(Money.franc(10), fiveCHF.times(2));
        assertEquals(Money.franc(15), fiveCHF.times(3));

    }

    @Test
    @DisplayName("equals()")
    void testEquaility() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertTrue(Money.franc(5).equals(Money.franc(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertFalse(Money.dollar(5).equals(Money.franc(5)));
    }

    @Test
    @DisplayName("통화")
    void testCurrency() {
        assertEquals("USD", Money.dollar(5).currency());
        assertEquals("CHF", Money.franc(5).currency());
    }

    @Test
    @DisplayName("Sum.plus")
    void testPlusReturnSum() {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);
    }

    @Test
    @DisplayName("$5 + $5에서 Money반환하기")
    void testReduceSum() {
        Expression sum = new Sum(Money.dollar(5), Money.dollar(5));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), result);
    }

    @Test
    @DisplayName("Sub.minus")
    void testReduceMinus() {
        Money five = Money.dollar(5);
        Expression result = five.minus(five);
        Sub sub = (Sub) result;
        assertEquals(five, sub.minuend);
        assertEquals(five, sub.subtrahend);
    }

    @Test
    @DisplayName("$10 - $5에서 Money반환하기")
    void testReduceSub() {
        Expression sub = new Sub(Money.dollar(10), Money.dollar(5));
        Bank bank = new Bank();
        Money result = bank.reduce(sub, "USD");
        assertEquals(Money.dollar(5), result);
    }

    @Test
    @DisplayName("2USD = 1CHF")
    void testUSDToCHFExchange() {
        Bank bank = new Bank();
        bank.addRate("USD", "CHF", BigDecimal.valueOf(0.5));
        Money result = bank.reduce(Money.dollar(2), "CHF");
        assertEquals(Money.franc(1), result);
    }

    @Test
    @DisplayName("1CHF = 2CHF")
    void testCHFToUSDExchange() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD",  BigDecimal.valueOf(2));
        Money result = bank.reduce(Money.franc(1), "USD");
        assertEquals(Money.dollar(2), result);
    }

    @Test
    @DisplayName("1USD + 2CHF = 5USD")
    void testMixedAddtion() {
        Expression oneUSD = Money.dollar(1);
        Expression twoCHF = Money.franc(2);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", BigDecimal.valueOf(2));
        Expression sum = oneUSD.plus(twoCHF);
        Money result = sum.reduce(bank, "USD");
        assertEquals(Money.dollar(5), result);
    }

    @Test
    @DisplayName("1CHF - 1USD = 1USD")
    void testMixedMinus() {
        Expression oneUSD = Money.dollar(1);
        Expression oneCHF = Money.franc(1);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", BigDecimal.valueOf(2));
        Expression minus = oneCHF.minus(oneUSD);
        Money result = minus.reduce(bank, "USD");
        assertEquals(Money.dollar(1), result);
    }
}
