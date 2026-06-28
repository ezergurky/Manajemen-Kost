package interfaces;

import java.util.List;


public interface CRUDRepository<T, ID> {
    public void tambah(T data);
    public boolean update(T data);
    public boolean hapus(ID id);
    public T cariById(ID id);
    public List<T> tampilkanSemua();
}
