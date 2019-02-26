package MoneyFlow;

import java.io.Serializable;

public class MoneyFlowCategory implements Serializable {
    private Long categoryId = System.currentTimeMillis();
    private String name;

    public MoneyFlowCategory(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}

