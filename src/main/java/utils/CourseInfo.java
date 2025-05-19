package utils;

public record CourseInfo(String title, int price) {
    @Override
    public String toString() {
        return title + " — " + price + " ₽";
    }
}