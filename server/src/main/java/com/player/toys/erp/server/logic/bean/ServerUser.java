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
    * 
    * </p>
*
* @author Player
* @since 2019-05-16
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class ServerUser extends Model<ServerUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private String username;

    private String password;

    private String salt;

    private String statu;

    private String stime;

    private String lastime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
