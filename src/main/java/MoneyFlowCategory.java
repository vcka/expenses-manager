import java.io.Serializable;

class MoneyFlowCategory implements Serializable {
    private Long categoryId = System.currentTimeMillis();
    private String name;

    MoneyFlowCategory(String name) {
        this.name = name;
    }

    Long getCategoryId() {
        return categoryId;
    }

    String getName() {
        return name;
    }
}

