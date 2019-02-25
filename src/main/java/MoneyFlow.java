import java.io.Serializable;
import java.util.Date;

class MoneyFlow implements Serializable {

    private Long categoryId;
    private Double amount;
    private Date date;
    private String description;

    MoneyFlow() {
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

    String  getDescription() {
        return description;
    }

    void setDescription(String  description) {
        this.description = description;
    }
}
