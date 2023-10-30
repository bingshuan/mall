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
 * @date 2023/10/26 20:51
 */
@Component
public class DelItemVisitor implements ItemVisitor<AbstractProductItem>{
    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        //从redis中获取当前数据
        ProductComposite currentItem = (ProductComposite) redisProcessor.get("items");
        //需要删除的商品类目
        ProductComposite delItem = (ProductComposite) productItem;
        //不可删除根节点
        if(delItem.getId() == currentItem.getId()) {
            throw new UnsupportedOperationException("父节点不能删除！");
        }
        //如果删除节点的父节点为当前节点，则直接删除
        if (delItem.getPid() == currentItem.getId()){
            currentItem.deleteProductChild(delItem);
            return currentItem;
        }
        //通过delChild方法进行递归寻找删除类目的插入点
        delChild(delItem,currentItem);
        return currentItem;
    }

    /**
     *递归寻找删除类目
     */
    private void delChild(ProductComposite delItem, ProductComposite currentItem) {
        for (AbstractProductItem abstractItem : currentItem.getChild()) {
            ProductComposite item = (ProductComposite) abstractItem;
            if ( delItem.getPid() == item.getId()) {
                item.deleteProductChild(delItem);
            }else {
                delChild(delItem,item);
            }
        }
    }
}
