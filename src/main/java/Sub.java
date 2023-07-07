public class Sub implements  Expression{

    Expression minuend;
    Expression subtrahend;

    public Sub(Expression minuend, Expression subtrahend) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    @Override
    public Money reduce(Bank bank, String to) {
        int amount = minuend.reduce(bank, to).amount - subtrahend.reduce(bank, to).amount;
        return new Money(amount, to);
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Expression minus(Expression subtrahend) {
        return new Sub(this, subtrahend);
    }

    @Override
    public Expression times(int multiplier) {
        return new Sub(minuend.times(multiplier), subtrahend.times(multiplier));
    }
}
