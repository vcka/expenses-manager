import java.io.Serializable;

public class IncomeCategory implements Serializable {
    private Long categoryId = System.currentTimeMillis();
    private String name;

    IncomeCategory(String name) {
        this.name = name;
        System.out.println("Category created.");
    }

    Long getCategoryId() {
        return categoryId;
    }

    String getName() {
        return name;
    }
}
