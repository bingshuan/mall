package com.study.mall.controller;

import com.study.mall.items.composite.ProductComposite;
import com.study.mall.pojo.ProductItem;
import com.study.mall.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author peng
 * @version 1.0
 * @description 商品类目控制层
 * @date 2023/10/25 22:13
 */
@RestController
@RequestMapping("/product")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    /**
     * 商品类目查询
     */
    @GetMapping("/fetchAllItems")
    public ProductComposite fetchAllItems() {
        return productItemService.fetchAllItems();
    }

    @PostMapping("/addItems")
    public ProductComposite addItems(@RequestBody ProductItem item) {
        return productItemService.addItems(item);
    }

    @PostMapping("/delItems")
    public ProductComposite delItems(@RequestBody ProductItem item) {
        return productItemService.delItems(item);
    }
}
