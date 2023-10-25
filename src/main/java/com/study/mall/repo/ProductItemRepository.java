package com.study.mall.repo;

import com.study.mall.pojo.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author peng
 * @version 1.0
 * @description 商品类目持久层
 * @date 2023/10/25 21:42
 */
@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,Integer> {

}
