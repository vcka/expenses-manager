import java.io.Serializable;
import java.util.Date;

public class MoneyFlow<C,A,D,F> implements Serializable {

    private C categoryId;
    private A amount;
    private D date;
    private F description;

    MoneyFlow() {
    }

    public C getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(C categoryId) {
        this.categoryId = categoryId;
    }

    public A getAmount() {
        return amount;
    }

    public void setAmount(A amount) {
        this.amount = amount;
    }

    public D getDate() {
        return date;
    }

    public void setDate(D date) {
        this.date = date;
    }

    public F getDescription() {
        return description;
    }

    public void setDescription(F description) {
        this.description = description;
    }
}
