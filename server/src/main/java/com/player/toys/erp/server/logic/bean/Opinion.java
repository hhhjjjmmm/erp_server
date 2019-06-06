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
    * 意见表; InnoDB free: 3072 kB
    * </p>
*
* @author Player
* @since 2019-05-21
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Opinion extends Model<Opinion> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String content;
    /*
     * 状态
     * */
    private String state;

    private String otime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
