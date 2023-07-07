public interface Expression {

    public Money reduce(Bank bank, String to);
    public Expression plus(Expression addend);

    public Expression times(int multiplier);

    public Expression minus(Expression subtrahend);
}
