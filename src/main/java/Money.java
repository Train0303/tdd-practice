import java.math.BigDecimal;
import java.util.Objects;

public class Money implements Expression{
    protected int amount;
    private String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money franc(int n) { return new Money(n, "CHF"); }
    public static Money dollar(int n) {
        return new Money(n, "USD");
    }

    public String currency() {
        return currency;
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Expression times(int multiplier) {
        return new Money(this.amount*multiplier, this.currency);
    }

    @Override
    public Expression minus(Expression subtrahend) { return new Sub(this, subtrahend);}

    @Override
    public Money reduce(Bank bank, String to) {
        return new Money(BigDecimal.valueOf(this.amount).multiply(bank.rate(this.currency, to)).intValue(), to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

}
