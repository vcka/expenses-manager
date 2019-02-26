package MoneyFlow;

import java.io.Serializable;
import java.util.Date;

public class MoneyFlow implements Serializable {

    private Long categoryId;
    private Double amount;
    private Date date;
    private String description;

    public MoneyFlow() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String  getDescription() {
        return description;
    }

    public void setDescription(String  description) {
        this.description = description;
    }
}
