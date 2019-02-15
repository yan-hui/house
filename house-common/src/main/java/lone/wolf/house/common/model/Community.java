package lone.wolf.house.common.model;

/**
 * @description: 小区实体类
 * @author: hch
 * @create: 2019/2/15$ 23:07$
 * @version: v1.0
 */
public class Community {
    /**
     * 主键id
     */
    private Integer id;
    /**
     *城市代码
     */
    private String cityCode;
    /**
     *城市名称
     */
    private String cityName;
    /**
     *小区名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Community{" + "id=" + id + ", cityCode='" + cityCode + '\'' + ", cityName='" + cityName + '\'' + ", name='" + name + '\'' + '}';
    }
}
