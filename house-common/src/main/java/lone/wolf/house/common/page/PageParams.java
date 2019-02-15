package lone.wolf.house.common.page;

/**
 * @description: 分页参数
 * @author: hch
 * @create: 2019/2/15$ 22:27$
 * @version: v1.0
 */
public class PageParams {
    private static final Integer PAGE_SIZE = 2;
    private Integer pageSize;
    private Integer pageNum;
    private Integer offset;
    private Integer limit;

    public PageParams() {
        this(PAGE_SIZE, 1);
    }

    public PageParams(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.offset = pageSize * (pageNum - 1);
        this.limit = pageSize;
    }

    public static PageParams build(Integer pageSize, Integer pageNum) {
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        if (null == pageNum) {
            pageNum = 1;
        }
        return new PageParams(pageSize, pageNum);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
