import java.util.HashMap;

public abstract class Controller<T> {
    DAO<T> dao;
    Controller(DAO<T> dao){
        this.dao = dao;
    }

    public HashMap<Integer,T> findAll(){
        return dao.findAll();
    }

    public abstract boolean add(T t);



}
