package com.study.mall.repo;

import com.study.mall.pojo.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author peng
 * @version 1.0
 * @description 商品类目持久层
 * @date 2023/10/25 21:42
 */
@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,Integer> {
    /**
     *向数据库添加新的商品类目
     */
    @Modifying
    @Query(value = "insert into product_item (id,name,pid)" +
            "values ((select max(id) from product_item, ?1, ?2))",nativeQuery = true)
    public void addItem(String name, int pid);

    /**
     * 删除商品类目及直接子目录
     */
    @Modifying
    @Query(value = "delete from product_item where" +
            "id = ?1 or pid = ?1", nativeQuery = true)
    public void delItem(int id);

    public ProductItem findByNameAndPid(String name, int pid);
}
