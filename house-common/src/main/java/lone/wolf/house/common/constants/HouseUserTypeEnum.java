package lone.wolf.house.common.constants;

/**
 * @description: 类型枚举值
 * @author: hch
 * @create: 2019/2/19 17:34
 * @version: v1.0
 */
public enum HouseUserTypeEnum {
    /**
     * 售卖
     */
    SALE(1),
    /**
     * 收藏
     */
    BOOKMARK(2);

    public final Integer value;

    private HouseUserTypeEnum(Integer value) {
        this.value = value;
    }

}
