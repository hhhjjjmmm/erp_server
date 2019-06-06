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
    * 文章表; InnoDB free: 3072 kB
    * </p>
*
* @author Player
* @since 2019-05-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class School extends Model<School> {

    private static final long serialVersionUID = 1L;

            /**
            * #文章id 主键 自增长
            */
            @TableId(value = "solid", type = IdType.AUTO)
    private Integer solid;

            /**
            * #文章标题
            */
    private String soltitle;

            /**
            * #文章副标题
            */
    private String soltitles;

            /**
            * #文字内容
            */
    private String solcontent;

            /**
            * #创建时间
            */
    private String soltime;


    @Override
    protected Serializable pkVal() {
        return this.solid;
    }

}
