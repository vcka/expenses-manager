import java.io.Serializable;

class MoneyFlowCategory<N> implements Serializable {
    private Long categoryId = System.currentTimeMillis();
    private N name;

    MoneyFlowCategory(N name) {
        this.name = name;
    }

    Long getCategoryId() {
        return categoryId;
    }

    N getName() {
        return name;
    }
}

