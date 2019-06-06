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
    * 销票员工表
    * </p>
*
* @author Player
* @since 2019-06-04
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Insertuser extends Model<Insertuser> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 用户名
            */
    private String iname;

            /**
            * 密码
            */
    private String ipassword;

            /**
            * 创建时间
            */
    private String pictime;

    private String pstatu;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
