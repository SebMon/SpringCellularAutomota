package dk.sdu.se4.cellular.common.data;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals (final Object O) {
        if (!(O instanceof Position)) return false;
        if (((Position) O).x != x) return false;
        if (((Position) O).y != y) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (x << 16) + y;
    }
}
