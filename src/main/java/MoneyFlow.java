import java.io.Serializable;
import java.util.Date;

class MoneyFlow<C,A,D,F> implements Serializable {

    private C categoryId;
    private A amount;
    private D date;
    private F description;

    MoneyFlow() {
    }

    C getCategoryId() {
        return categoryId;
    }

    void setCategoryId(C categoryId) {
        this.categoryId = categoryId;
    }

    A getAmount() {
        return amount;
    }

    void setAmount(A amount) {
        this.amount = amount;
    }

    D getDate() {
        return date;
    }

    void setDate(D date) {
        this.date = date;
    }

    F getDescription() {
        return description;
    }

    void setDescription(F description) {
        this.description = description;
    }
}
