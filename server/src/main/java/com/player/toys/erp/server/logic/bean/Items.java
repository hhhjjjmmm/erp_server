package com.player.toys.erp.server.logic.bean;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import java.util.Date;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * InnoDB free: 3072 kB
    * </p>
*
* @author Player
* @since 2019-05-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Items extends Model<Items> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 商品图片
            */
    private String pic;

            /**
            * 商品名称
            */
    private String name;

            /**
            * 商品类别
            */
    private String gory;

            /**
            * 活动开始时间
            */
    private String gotime;

            /**
            * 活动结束时间
            */
    private String sdtime;

            /**
            * 商品定价
            */
    private Float price;

            /**
            * 活动价
            */
    private Float prices;

            /**
            * 商品描述
            */
    private String detail;

            /**
            * 地址
            */
    private String address;

            /**
            * 特别提示
            */
    private String tips;
    /**
     * 推广金额
     */
    private Float popul;
    /**
     * 团长金额
     */
    private Float head;
    /**
     * 基本信息
     */
    private String basic;
    /**
     * 平台声明
     */
    private String platform;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
