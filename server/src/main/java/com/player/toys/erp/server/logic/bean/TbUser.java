package com.player.toys.erp.server.logic.bean;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;

    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 后台用户表; InnoDB free: 3072 kB
    * </p>
*
* @author Player
* @since 2019-05-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class TbUser extends Model<TbUser> {

    private static final long serialVersionUID = 1L;

            /**
            * #用户编号 整形 主键 自增长
            */
            @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

            /**
            * #头像
            */
    private String uimg;

            /**
            * #店主昵称
            */
    private String uname;

            /**
            * #手机号
            */
    private String uphone;

    private String upasswrod;

            /**
            * #店铺ID
            */
    private String ushop;

            /**
            * #店铺名称
            */
    private String ushopname;

            /**
            * #创建时间
            */
    private String ucreatetime;
            /**
            * #创建时间
            */
    private int stuts;
     /**
     * #创建时间
     */
    private int stutsto;
    /**
     * #创建时间
     */
    private String openid;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
