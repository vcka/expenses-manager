package MoneyFlow;

import java.io.IOException;
import java.util.List;

public interface Repository<T> {
    List<T> getList();
    void setList(List<T> list);
    void dataSave() throws IOException;
    void dataLoad() throws IOException, ClassNotFoundException;
}
