package com.study.mall.service;

import com.study.mall.items.composite.AbstractProductItem;
import com.study.mall.items.composite.ProductComposite;
import com.study.mall.items.vistor.AddItemVisitor;
import com.study.mall.items.vistor.DelItemVisitor;
import com.study.mall.pojo.ProductItem;
import com.study.mall.repo.ProductItemRepository;
import com.study.mall.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author peng
 * @version 1.0
 * @description 商品类目业务层
 * @date 2023/10/25 21:44
 */
@Service
@Transactional
public class ProductItemService {
    @Autowired
    private RedisCommonProcessor redisProcessor;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private AddItemVisitor addItemVisitor;
    @Autowired
    private DelItemVisitor delItemVisitor;

    /**
     * 获取商品类目信息
     * @return
     */
    public ProductComposite fetchAllItems() {
        //1、先查询redis,如果不为null,直接返回
        Object cacheItems = redisProcessor.get("items");
        if (cacheItems != null) {
            return (ProductComposite) cacheItems;
        }
        //2、如果redis为null,查询db,调用finaAll方法获取所有的商品
        List<ProductItem> fetchDbItems = productItemRepository.findAll();
        //3、将查询结果拼装成组合模式的树形结构
        ProductComposite items = generateProductTree(fetchDbItems);
        if (items == null){
            throw new UnsupportedOperationException("Product items should not be empty in DB");
        }
        //4、将商品信息设置到redis中
        redisProcessor.set("items",items);
        return items;
    }

    /**
     * 添加商品类目
     */
    public ProductComposite addItems(ProductItem item) {
        //先更新数据库
        productItemRepository.addItem(item.getName(),item.getPid());
        //通过访问者模式访问树形数据结构，并添加新的商品类目
        ProductComposite addItem = ProductComposite.builder()
                .id(productItemRepository.findByNameAndPid(item.getName(),item.getPid()).getId())
                .name(item.getName())
                .pid(item.getPid())
                .child(new ArrayList<>())
                .build();
        AbstractProductItem updatedItems = addItemVisitor.visitor(addItem);
        //更新redis (此处可以做重试机制，如果不成功，可以人工介入)
        redisProcessor.set("items",updatedItems);
        return (ProductComposite) updatedItems;
    }

    /**
     * 删除商品类目
     */
    public ProductComposite delItems(ProductItem item) {
        //先更新数据库
        productItemRepository.delItem(item.getId());
        //通过访问者模式访问树形数据结构，并删除新的商品类目
        ProductComposite delItem = ProductComposite.builder()
                .id(item.getId())
                .name(item.getName())
                .pid(item.getPid())
                .build();
        AbstractProductItem updatedItems = delItemVisitor.visitor(delItem);
        //更新redis (此处可以做重试机制，如果不成功，可以人工介入)
        redisProcessor.set("items",updatedItems);
        return (ProductComposite) updatedItems;
    }


    //生成树形结构
    private ProductComposite generateProductTree(List<ProductItem> fetchDbItems) {
        ArrayList<ProductComposite> composites = new ArrayList<>(fetchDbItems.size());
        fetchDbItems.forEach(dbItem -> composites.add(ProductComposite
                .builder()
                .id(dbItem.getId())
                .name(dbItem.getName())
                .pid(dbItem.getPid())
                .build()));
        Map<Integer, List<ProductComposite>> groupingList = composites.stream().collect(Collectors.groupingBy(ProductComposite::getPid));
        composites.stream()
                .forEach(item ->{
                    List<ProductComposite> list = groupingList.get(item.getId());
                    item.setChild(list == null ? new ArrayList<>() : list
                            .stream().map(x->(AbstractProductItem)x)
                            .collect(Collectors.toList()));
                });
        ProductComposite composite = composites.size() == 0 ? null : composites.get(0);
        return  composite;
    }
}
