package com.player.toys.erp.server.logic.bean;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 订单表; InnoDB free: 3072 kB; (`user_id`) REFER `boot/tb_user`(`uid`) ON DELETE NO 
    * </p>
*
* @author Player
* @since 2019-05-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 下单用户id
            */
    private Integer userId;

            /**
            * 订单号
            */
    private String number;

            /**
            * 商品名称
            */
    private String name;

            /**
            * 单价
            */
    private Float price;

            /**
            * 数量
            */
    private Integer onum;

            /**
            * 总价
            */
    private Float pricenum;

            /**
            * 手机号
            */
    private String calls;

            /**
            * 创建订单时间
            */
    private String createtime;

            /**
            * 核验状态
            */
    private String status;

            /**
            * 支付状态
            */
    private String ostatus;

    /**
     * 订单状态
     */
    private String states;

    /**
     * 二维码
     */
    private String opic;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
