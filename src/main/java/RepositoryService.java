import java.io.IOException;

public interface RepositoryService {
    void dataSave() throws IOException;
    void dataLoad() throws IOException, ClassNotFoundException;
}
