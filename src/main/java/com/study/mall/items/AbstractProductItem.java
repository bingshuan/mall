package com.study.mall.items;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/24 20:58
 */
public abstract class AbstractProductItem {
    //增加商品类目
    protected void addProductItem(AbstractProductItem item) {
        throw new UnsupportedOperationException(" Not Support child add!");
    }
    //移除商品类目
    protected void  deleteProductChild(AbstractProductItem item) {
        throw new UnsupportedOperationException("Not Support child remove!");
    }

}
