import java.io.Serializable;

class Category<N> implements Serializable {
    private Long categoryId = System.currentTimeMillis();
    private N name;

    Category(N name) {
        this.name = name;
        System.out.println("Category created.");
    }

    Long getCategoryId() {
        return categoryId;
    }

    N getName() {
        return name;
    }
}

