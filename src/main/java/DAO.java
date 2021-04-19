import java.util.HashMap;

public abstract class DAO<T> {
    public abstract HashMap<Integer, T> findAll();
    public abstract boolean add(T t);
    public abstract T get(int id);
    public abstract boolean delete(int id);
    public abstract boolean update(T t);
}
