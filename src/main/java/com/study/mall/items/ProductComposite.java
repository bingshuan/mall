package com.study.mall.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/25 21:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComposite extends AbstractProductItem {
    private int id;
    private int pid;
    private String name;
    private List<AbstractProductItem> child = new ArrayList<>();

    @Override
    protected void addProductItem(AbstractProductItem item) {
        child.add(item);
    }

    @Override
    protected void deleteProductChild(AbstractProductItem item) {
        ProductComposite removeItem = (ProductComposite) item;
        Iterator iterator = child.iterator();
        while (iterator.hasNext()) {
            ProductComposite composite = (ProductComposite) iterator.next();
            //移除相同的商品类目
            if (composite.getId() == removeItem.getId()) {
                iterator.remove();
                break;
            }
        }
    }
}
