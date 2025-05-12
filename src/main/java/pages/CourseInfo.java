package pages;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для информации о курсе.
 */
public class CourseInfo {
    private final String name;
    private final LocalDate startDate;
    private final BigDecimal price;

    public CourseInfo(String name, LocalDate startDate) {
        this.name = name;
        this.startDate = startDate;
        this.price = null;
    }

    public CourseInfo(String name, BigDecimal price) {
        this.name = name;
        this.startDate = null;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
