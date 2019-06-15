package de.tudresden.inf.st.ttc19.util;

import de.tudresden.inf.st.ttc19.jastadd.model.AbstractNode;

public class Pair implements Comparable {

  private final AbstractNode first;
  private final AbstractNode second;

  public Pair(AbstractNode left, AbstractNode right) {
    this.first = left;
    this.second = right;
  }

  public AbstractNode getFirst() {
    return first;
  }

  public AbstractNode getSecond() {
    return second;
  }

  @Override
  public int hashCode() {
    return first.hashCode() ^ second.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    Pair pairo = (Pair) o;
    return this.first.equals(pairo.getFirst()) &&
        this.second.equals(pairo.getSecond());
  }

  @Override
  public int compareTo(Object o) {
    if (o == null) throw new NullPointerException("Cannot compare null Pair");
    if (!(o instanceof Pair)) throw new ClassCastException("Cannot compare other objects to a Pair");

    Pair other = (Pair) o;

    // null is smaller than every hash code
    if (this.getFirst() == null) {
      if (this.getSecond() == null) {
        return 0;
      } else {
        return -1;
      }
    } else if (this.getSecond() == null) {
      return 1;
    }


    int firstComparison = Integer.compare(this.getFirst().hashCode(), other.getFirst().hashCode());
    if (firstComparison == 0) {
      return Integer.compare(this.getSecond().hashCode(), other.getSecond().hashCode());
    } else {
      return firstComparison;
    }

  }
}
