import java.util.HashMap;

public abstract class DAO<T> {
    public abstract HashMap<Integer, T> findAll();
    public abstract boolean add(T t);

}
