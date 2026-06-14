package interfaces;

import java.util.List;


public interface CRUDRepository<T, ID> {

    void tambah(T data);

    boolean update(T data);


    boolean hapus(ID id);

    T cariById(ID id);


    List<T> tampilkanSemua();
}
