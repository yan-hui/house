package lone.wolf.house.common.model;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @description: 房产实体类
 * @author: hch
 * @create: 2019/2/15$ 22:01$
 * @version: v1.0
 */
public class House {
    private Long id;
    /**
     * 房产名称
     */
    private String name;
    /**
     * 1-销售 2-出租 默认0
     */
    private Integer type;
    /**
     * 单位：元
     */
    private Integer price;
    /**
     * 房屋图片
     */
    private String images;
    /**
     * 面积
     */
    private Integer area;
    /**
     * 卧室数量
     */
    private Integer beds;
    /**
     * 卫生间数量
     */
    private Integer baths;
    /**
     * 评分
     */
    private Double rating;
    /**
     * 房产的描述
     */
    private String remarks;

    /**
     * 房产属性
     */
    private String properties;

    /**
     * 户型图
     */
    private String floorPlan;
    /**
     * 标签
     */
    private String tags;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 小区id
     */
    private Integer communityId;
    /**
     * 房产的地址
     */
    private String address;
    /**
     * 1-上架 -下架 默认 0
     */
    private Integer state;
    /**
     * 首个图片
     */
    private String firstImg;
    /**
     * 房屋图片列表
     */
    private List<String> imageList = Lists.newArrayList();
    /**
     * 户型图列表
     */
    private List<String> floorPlanList = Lists.newArrayList();
    /**
     * 房屋文件
     */
    private List<MultipartFile> houseFiles;
    /**
     * 户型图文件
     */
    private List<MultipartFile> floorPlanFiles;
    /**
     * 房屋的用户id
     */
    private Long userId;
    /**
     * 是否收藏
     */
    private boolean bookMarked;
    /**
     * 可能通过注册id查询
     */
    private List<Long> ids;
    /**
     * 排序方式
     */
    private String sort = "time_desc";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
        if (!Strings.isNullOrEmpty(images)) {
            List<String> list = Splitter.on(",").splitToList(images);
            if (!list.isEmpty()) {
                this.firstImg = list.get(0);
                this.imageList = list;
            }
        }
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getBaths() {
        return baths;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getFloorPlanList() {
        return floorPlanList;
    }

    public void setFloorPlanList(List<String> floorPlanList) {
        this.floorPlanList = floorPlanList;
    }

    public List<MultipartFile> getHouseFiles() {
        return houseFiles;
    }

    public void setHouseFiles(List<MultipartFile> houseFiles) {
        this.houseFiles = houseFiles;
    }

    public List<MultipartFile> getFloorPlanFiles() {
        return floorPlanFiles;
    }

    public void setFloorPlanFiles(List<MultipartFile> floorPlanFiles) {
        this.floorPlanFiles = floorPlanFiles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isBookMarked() {
        return bookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        this.bookMarked = bookMarked;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "House{" + "id=" + id + ", name='" + name + '\'' + ", type=" + type + ", price=" + price + ", images='" + images + '\'' + ", area=" + area + ", beds=" + beds + ", baths=" + baths + ", rating=" + rating + ", remarks='" + remarks + '\'' + ", properties='" + properties + '\'' + ", floorPlan='" + floorPlan + '\'' + ", tags='" + tags + '\'' + ", createTime=" + createTime + ", cityId=" + cityId + ", communityId=" + communityId + ", address='" + address + '\'' + ", state=" + state + ", firstImg='" + firstImg + '\'' + ", imageList=" + imageList + ", floorPlanList=" + floorPlanList + ", houseFiles=" + houseFiles + ", floorPlanFiles=" + floorPlanFiles + ", userId=" + userId + ", bookMarked=" + bookMarked + ", ids=" + ids + '}';
    }
}
