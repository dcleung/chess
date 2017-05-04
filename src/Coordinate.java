class Coordinate implements Comparable<Coordinate>{
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

	@Override
	public String toString() {
		return "(" + this.getX() + "," + this.getY() + ")";
	}

	@Override
	public int compareTo(Coordinate o) {
		if (o.getClass() != this.getClass())
			return -1;
		if (((Coordinate) o).x == this.x && ((Coordinate) o).y == this.y)
			return 0;
		if (this.x > o.x)
			return 1;
		return -1;
	}
	
	@Override
	public boolean equals(Object o) 
	{
	    if (o instanceof Coordinate) 
	    {
	      Coordinate c = (Coordinate) o;
	      if ( this.getX() == ((Coordinate) o).getX() && this.getY() == ((Coordinate) o).getY() )
	         return true;
	    }
	    return false;
	}
}
