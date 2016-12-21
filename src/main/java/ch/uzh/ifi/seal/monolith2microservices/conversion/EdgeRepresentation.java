package ch.uzh.ifi.seal.monolith2microservices.conversion;

/**
 * Created by gmazlami on 12/20/16.
 */
public class EdgeRepresentation {

    private long from;

    private long to;

    public EdgeRepresentation(long from, long to){
        this.from = from;
        this.to = to;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeRepresentation)) return false;

        EdgeRepresentation that = (EdgeRepresentation) o;

        if (from != that.from) return false;
        return to == that.to;

    }

    @Override
    public int hashCode() {
        int result = (int) (from ^ (from >>> 32));
        result = 31 * result + (int) (to ^ (to >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "EdgeRepresentation{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
