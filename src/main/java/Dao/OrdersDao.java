package Dao;

import com.aaa.Orders;

import java.util.List;

public interface OrdersDao {
    public Orders getByNo(String no);

    public List<Orders> getAll();
}
