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
    * 商户表; InnoDB free: 3072 kB
    * </p>
*
* @author Player
* @since 2019-05-21
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Merchant extends Model<Merchant> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /**
            * 商户名称
            */
    private String mname;

            /**
            * 手机号码
            */
    private String calls;

            /**
            * 产品名称
            */
    private String proname;

            /**
            * 合作产品
            */
    private String hzpro;
    /*
    * 状态
    * */
    private String state;


    /**
            * 申请时间
            */
    private String mtime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
