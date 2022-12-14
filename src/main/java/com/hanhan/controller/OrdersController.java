package com.hanhan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hanhan.common.BaseContex;
import com.hanhan.common.R;
import com.hanhan.entity.Orders;
import com.hanhan.service.OrderDetailService;
import com.hanhan.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("oders:{}",orders);
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 客户端订单页面
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPage(Integer page,Integer pageSize){
        Page<Orders> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Orders::getUserId, BaseContex.getCurrentId());

        ordersService.page(pageInfo, lqw);
        return R.success(pageInfo);
    }

    /**
     * 管理端订单页面
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize){
        Page<Orders> pageInfo = new Page<>(page,pageSize);

        //select * from Orders
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();

        ordersService.page(pageInfo, lqw);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<Orders> updateStatus(@RequestBody Orders orders){
        Orders order = ordersService.getById(orders.getId());
        order.setStatus(orders.getStatus());
        ordersService.updateById(order);
        return R.success(order);
    }

}
