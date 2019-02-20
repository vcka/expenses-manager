import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {

    private Long categoryId;
    private Double amount;
    private Date date;
    private String description;

    Expense() {
    }

    Long getCategoryId() {
        return categoryId;
    }

    void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    Double getAmount() {
        return amount;
    }

    void setAmount(Double amount) {
        this.amount = amount;
    }

    Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }
}
