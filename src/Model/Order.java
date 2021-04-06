package Model;


public class Order {
    private int orderID;
    private int table;
    private int day;
    private int month;
    private int year;

    public Order(int orderID, int table, int day, int month, int year) {
        this.orderID = orderID;
        this.table = table;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderID == order.orderID &&
                table == order.table &&
                day == order.day &&
                month == order.month &&
                year == order.year;
    }

    @Override
    public int hashCode() {
        int hashCode = 12;
        hashCode += 5*hashCode + 30*orderID + 5*day + 9*month + year + 4*table;
        return hashCode;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
