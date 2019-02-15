package lone.wolf.house.common.page;

import java.util.List;

/**
 * @description: 分页数据结果类
 * @author: hch
 * @create: 2019/2/15 23:21
 * @version: v1.0
 */
public class PageData<T> {
    private List<T> list;
    private Pagination pagination;

    public static <T> PageData<T> buildPage(List<T> list, long count, Integer pageSize, Integer pageNum) {
        Pagination pagination = new Pagination(pageSize, pageNum, count);
        return new PageData<>(list, pagination);
    }

    public PageData(List<T> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
