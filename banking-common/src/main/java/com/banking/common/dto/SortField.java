package com.banking.common.dto;

/**
 * Sort field definition for query sorting.
 */
public class SortField {

    private String field;
    private Direction direction;

    public SortField() {
        this.direction = Direction.ASC;
    }

    public SortField(String field, Direction direction) {
        this.field = field;
        this.direction = direction != null ? direction : Direction.ASC;
    }

    public static SortField asc(String field) {
        return new SortField(field, Direction.ASC);
    }

    public static SortField desc(String field) {
        return new SortField(field, Direction.DESC);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isAscending() {
        return direction == Direction.ASC;
    }

    public enum Direction {
        ASC,
        DESC
    }
}
