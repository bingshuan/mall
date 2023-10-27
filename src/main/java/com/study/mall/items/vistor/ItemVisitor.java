package com.study.mall.items.vistor;

import com.study.mall.items.composite.AbstractProductItem;

/**
 * @author peng
 * @version 1.0
 * @description Vistor抽象访问者
 * 此处使用泛型T进行接口定义，提高代码的拓展性
 * @date 2023/10/26 20:23
 */
public interface ItemVisitor<T> {
    //定义公共的 vistor方法供子类实现
    T visitor(AbstractProductItem productItem);
}

