import java.io.Serializable;

public class IncomeCategory implements Serializable {
    private Long incomeCategoryId = System.currentTimeMillis();
    private String name;

    IncomeCategory(String name) {
        this.name = name;
        System.out.println("Category created.");
    }

    Long getIncomeCategoryId() {
        return incomeCategoryId;
    }

    String getName() {
        return name;
    }
}
