package org.jeecg.modules.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.order.entity.CtBRequestParams;
import org.jeecg.modules.order.entity.Orders;
import org.jeecg.modules.order.entity.Pay;
import org.jeecg.modules.order.entity.RequestParams;
import org.jeecg.modules.order.lib.SybPayService;
import org.jeecg.modules.order.service.IOrdersService;
import org.jeecg.modules.order.service.IPayService;
import org.jeecg.modules.order.vo.OrdersPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order/pay")
@Slf4j
public class SybPayController {

    @Autowired
    private IOrdersService ordersService;

    @Autowired
    private IPayService payService;

    /**
     * 交易查询
     * @throws Exception
     */
    @GetMapping(value = "/query")
    public Result<?> query(@RequestParam(name="reqsn",required=true) String reqsn,@RequestParam(name="trxid",required=true) String trxid) throws Exception{
        System.out.println("查询接口，接收到的参数  reqsn:"+reqsn+",trxid:"+trxid+"***************************");
        SybPayService service = new SybPayService();
        Map<String, String> map = service.query(reqsn, trxid);
        print(map);

        if("0000".equals(map.get("trxstatus"))){
            //订单表
            OrdersPage ordersPage = new OrdersPage();
            ordersPage.setId(reqsn);//主键-订单号
            ordersPage.setPayStatus("支付完成");//支付状态

            //支付表
            Pay pay = new Pay();
            pay.setOrderId(reqsn);//订单号
            pay.setSn(map.get("trxid"));//交易流水号
            pay.setHostDate(new Date());//交易完成时间
            pay.setAmount(new BigDecimal(map.get("initamt")));//原交易金额
            pay.setPayStatus("支付完成");//支付状态

            List<Pay> list = new ArrayList<>();
            list.add(pay);

            ordersPage.setPayList(list);
            System.out.println("^^^^^^^^^^^^^^^^^封装好了"+ordersPage.toString()+"^^^^^^^^^^^^^^^^^^");

            Orders orders = new Orders();
            BeanUtils.copyProperties(ordersPage, orders);
            Orders ordersEntity = ordersService.getById(orders.getId());
            if(ordersEntity==null) {
                return Result.error("未找到对应数据");
            }
            ordersService.updateMain(orders, ordersPage.getPayList());
        }
        return Result.ok(map);

    }

    /**
     * 交易退款
     * @throws Exception
     */
    @GetMapping(value = "/refund")
    public Result<?> refund(@RequestParam(name="id",required=true) String id) throws Exception{

        Orders orders = ordersService.getById(id);
        System.out.println("交易退款，查询到："+orders.toString());
        if(!("支付完成".equals(orders.getPayStatus().trim()))){
            return Result.error("退款失败！该笔交易未支付完成");
        }
        SybPayService service = new SybPayService();
        String reqsn = String.valueOf(System.currentTimeMillis());
        Map<String, String> map = service.refund(orders.getAmount().longValue(), reqsn, orders.getSn(), "");
        print(map);

        if("0000".equals(map.get("trxstatus"))){
            //更改主表支付状态，附表数据不变
            //订单表
            OrdersPage ordersPage = new OrdersPage();
            ordersPage.setId(orders.getId());//主键-订单号
            ordersPage.setPayStatus("已退款");//支付状态

            List<Pay> list = payService.selectPays(orders.getId());

            ordersPage.setPayList(list);
            System.out.println("^^^^^^^^^^^^^^^^^封装好了"+ordersPage.toString()+"^^^^^^^^^^^^^^^^^^");

            Orders order = new Orders();
            BeanUtils.copyProperties(ordersPage, order);
            Orders ordersEntity = ordersService.getById(order.getId());
            if(ordersEntity==null) {
                return Result.error("未找到对应数据");
            }
            ordersService.updateMain(order, ordersPage.getPayList());

            //往支付表中添加新纪录：退款记录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Pay pay = new Pay();
            pay.setOrderId(orders.getId());//原交易订单号
            pay.setSn(map.get("trxid"));//退款交易流水号
            pay.setHostDate(sdf.parse(map.get("fintime")));//交易完成时间
            pay.setAmount(new BigDecimal(orders.getAmount().longValue()));//原交易金额
            pay.setPayStatus("已退款");//支付状态

            payService.save(pay);
        }else{
            return Result.error("退款失败！"+map.get("errmsg"));
        }

        return Result.ok(map);
    }

    /**
     * 交易撤销
     * @throws Exception
     */
    @GetMapping(value = "/cancel")
    public Result<?> cancel(@RequestParam(name="id",required=true) String id) throws Exception{
        Orders orders = ordersService.getById(id);
        System.out.println("交易退款，查询到："+orders.toString());
        if(!("支付完成".equals(orders.getPayStatus().trim()))){
            return Result.error("撤销失败！该笔交易未支付完成");
        }

        SybPayService service = new SybPayService();
        String reqsn = String.valueOf(System.currentTimeMillis());
        Map<String, String> map = service.cancel(orders.getAmount().longValue(), reqsn, orders.getSn(), "");
        print(map);

        if("0000".equals(map.get("trxstatus"))){
            //更改主表支付状态，附表数据不变
            //订单表
            OrdersPage ordersPage = new OrdersPage();
            ordersPage.setId(orders.getId());//主键-订单号
            ordersPage.setPayStatus("已撤销");//支付状态

            List<Pay> list = payService.selectPays(orders.getId());

            ordersPage.setPayList(list);
            System.out.println("^^^^^^^^^^^^^^^^^封装好了"+ordersPage.toString()+"^^^^^^^^^^^^^^^^^^");

            Orders order = new Orders();
            BeanUtils.copyProperties(ordersPage, order);
            Orders ordersEntity = ordersService.getById(order.getId());
            if(ordersEntity==null) {
                return Result.error("未找到对应数据");
            }
            ordersService.updateMain(order, ordersPage.getPayList());

            //往支付表中添加新纪录：退款记录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Pay pay = new Pay();
            pay.setOrderId(orders.getId());//原交易订单号
            pay.setSn(map.get("trxid"));//退款交易流水号
            pay.setHostDate(sdf.parse(map.get("fintime")));//交易完成时间
            pay.setAmount(new BigDecimal(orders.getAmount().longValue()));//原交易金额
            pay.setPayStatus("已撤销");//支付状态

            payService.save(pay);
        }else{
            return Result.error("撤销失败！"+map.get("errmsg"));
        }

        return Result.ok(map);
    }


    /**
     * 统一扫码
     * @throws Exception
     */
    @PostMapping(value = "/scanqrpay")
    public Result<?> scanqrpay(@RequestBody(required=true) RequestParams params) throws Exception{
        log.info("统一扫码，params:{}",params.toString());
        SybPayService service = new SybPayService() ;
        String reqsn = String.valueOf(System.currentTimeMillis());
        Map<String,String> map = service.scanqrpay(params.getTrxamt(),reqsn,params.getBody(),params.getRemark(),params.getAuthcode(),params.getLimit_pay(),params.getGoods_tag(),
                params.getBenefitdetail(),params.getChnlstoreid(),params.getSubbranch(),params.getIdno(),params.getExtendparams(),params.getTruename(),params.getAsinfo(),params.getFqnum());
        print(map);

        //订单表
        OrdersPage ordersPage = new OrdersPage();
        ordersPage.setId(reqsn);//主键-订单号
        ordersPage.setSn(map.get("trxid"));//流水号
        ordersPage.setAmount(new BigDecimal(params.getTrxamt()));//交易金额
        ordersPage.setGoodsId(params.getGoodsId());//商品id
        ordersPage.setGoodsName(params.getGoodsName());//商品名称
        ordersPage.setPayStatus(map.get("errmsg"));//支付状态

        //支付表
        Pay pay = new Pay();
        pay.setSn(map.get("trxid"));//流水号
        pay.setAmount(new BigDecimal(params.getTrxamt()));//交易金额
        pay.setHostDate(new Date());//支付时间
        pay.setOrderId(reqsn);//订单号
        pay.setPayStatus(map.get("errmsg"));//支付状态

        List<Pay> list = new ArrayList<>();
        list.add(pay);

        ordersPage.setPayList(list);
        log.info("交易数据存，ordersPage={}",ordersPage.toString());

        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersPage, orders);
        ordersService.saveMain(orders, ordersPage.getPayList());

        return Result.ok(map);
    }


    /**
     * 统一支付
     * @throws Exception
     */
    @PostMapping(value = "/pay")
    public Result<?> pay(@RequestBody(required = true)CtBRequestParams params) throws Exception{
        SybPayService service = new SybPayService();
        String reqsn = String.valueOf(System.currentTimeMillis());
        Map<String, String> map = service.pay(params.getTrxamt(), reqsn, params.getPaytype(), params.getBody(), params.getRemark(), params.getAcct(), "",
                params.getNotify_url(),params.getLimit_pay(),params.getIdno(),params.getTruename(),params.getAsinfo());
        print(map);

        return Result.ok(map);
    }

    public static void print(Map<String, String> map){
        System.out.println("返回数据如下:");
        if(map!=null){
            for(String key:map.keySet()){
                System.out.println(key+";"+map.get(key));
            }
        }
    }
}
