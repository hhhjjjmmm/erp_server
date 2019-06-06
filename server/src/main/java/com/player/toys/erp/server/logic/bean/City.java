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
public class City extends Model<City> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 城市服务名称
     */
    private String cname;

    /**
     * 手机号码
     */
    private String calls;

    /**
     * 公司名称
     */
    private String gsname;

    /**
     * 行业
     */
    private String hyname;

    /**
     * 所在城市
     */
    private String cityname;

    /**
     * 是否有近期合作
     */
    private String bolean;

    /**
     * 申请项目名称
     */
    private String xmname;
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
