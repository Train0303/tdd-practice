import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Objects;

public class Bank {

    private Hashtable<Pair, BigDecimal> hashtable = new Hashtable();

    public Money reduce(Expression source, String to) {
        return source.reduce(this, to);
    }

    public BigDecimal rate(String from, String to) {
        if(from.equals(to)) {
            return BigDecimal.valueOf(1);
        }
        return hashtable.get(new Pair(from, to));
    }

    public void addRate(String from, String to, BigDecimal rate) {
        hashtable.put(new Pair(from, to), rate);
    }


    private static class Pair {
        private final String from;
        private final String to;

        public Pair(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(from, pair.from) && Objects.equals(to, pair.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }
}
