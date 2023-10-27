package com.study.mall.items.vistor;

import com.study.mall.items.composite.AbstractProductItem;
import com.study.mall.items.composite.ProductComposite;
import com.study.mall.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @version 1.0
 * @description ConcreteVisitor具体访问者
 * @date 2023/10/26 20:29
 */
@Component
public class AddItemVisitor implements ItemVisitor<AbstractProductItem> {
    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        //从redis中获取当前数据
        ProductComposite currentItem = (ProductComposite) redisProcessor.get("items");
        //需要新增的商品类目
        ProductComposite addItem = (ProductComposite) productItem;
        //如果新增节点的父节点为当前节点，则直接添加
        if (addItem.getPid() == currentItem.getId()){
            currentItem.addProductItem(addItem);
            return currentItem;
        }
        //通过addChild方法进行递归寻找新增类目的插入点
        addChild(addItem,currentItem);
        return currentItem;
    }

    /**
     * 递归寻找新增类目的插入点
     */
    private void addChild(ProductComposite addItem, ProductComposite currentItem) {
        for (AbstractProductItem abstractItem : currentItem.getChild()){
            ProductComposite item = (ProductComposite) abstractItem;
            if (addItem.getPid() == item.getPid()){
                item.addProductItem(addItem);
            }else {
                addChild(addItem,item);
            }
        }
    }
}
