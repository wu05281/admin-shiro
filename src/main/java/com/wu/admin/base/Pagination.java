package com.wu.admin.base;

import java.io.Serializable;
import java.util.List;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午5:29
 */
public class Pagination<T> implements Serializable {

    private static final Long serialVersionUID = -5884976706259160221L;

    public static Integer DEFAULT_PAGE_SIZE = 1;

    public static Integer DEFAULT_PAGE_INDEX = 1;
    /**
     * 上一页
     */
    private Long preIndex;
    /**
     * 当前页
     */
    private Long curIndex;
    /**
     * 下一页
     */
    private Long nextIndex;
    /**
     * 每页条数
     */
    private Long pageSize;
    /**
     * 总条数
     */
    private Long rowsCount;

    public void setPreIndex(Long preIndex) {
        this.preIndex = preIndex;
    }

    public void setCurIndex(Long curIndex) {
        this.curIndex = curIndex;
    }

    public void setNextIndex(Long nextIndex) {
        this.nextIndex = nextIndex;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 总页数
     */
    private Long pagesCount;
    /**
     * 对象列表
     */
    private List<T> items;

    /**
     *
     * 分页类构建函数
     *
     */
    public Pagination() {
        updateInfo(0L, 0L, 0L);
    }

    /**
     *
     * 分页类构建函数
     *
     * @param pageIndex
     *            当前页码
     * @param pageSize
     *            每页记录数
     */
    public Pagination(Long pageIndex, Long pageSize) {
        updateInfo(pageIndex, pageSize, 0L);
    }

    /**
     * 分页类构建函数
     *
     * @param pageIndex
     *            当前页码
     * @param pageSize
     *            每页记录数
     * @param rowsCount
     *            记录总数
     */
    public Pagination(Long pageIndex, Long pageSize, Long rowsCount) {
        updateInfo(pageIndex, pageSize, rowsCount);
    }

    /**
     * 获取当前面记录
     *
     * @return
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * 设置当前页记录
     *
     * @param items
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * 获取当前页码
     *
     * @return
     */
    public Long getCurIndex() {
        return curIndex;
    }

    /**
     * 获取下一页码
     *
     * @return
     */
    public Long getNextIndex() {
        return nextIndex;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public Long getPagesCount() {
        return pagesCount;
    }

    /**
     * 获取每页记录数
     *
     * @return
     */
    public Long getPageSize() {
        return pageSize;
    }

    /**
     * 获取上一页码
     *
     * @return
     */
    public Long getPreIndex() {
        return preIndex;
    }

    /**
     * 获取总记录数
     *
     * @return
     */
    public Long getRowsCount() {
        return rowsCount;
    }

    /**
     * 获取首页码
     *
     * @return
     */
    public Long getFirstIndex() {
        return 1L;
    }

    /**
     * 获取末页码
     *
     * @return
     */
    public Long getLastIndex() {
        return pagesCount;
    }

    private void updateInfo(Long pageIndex, Long pageSize, Long rowsCount) {

        if (pageSize > 0) {

            this.curIndex = pageIndex;
            this.rowsCount = rowsCount;
            this.pageSize = pageSize;

            // 确定页数
            pagesCount = (rowsCount + pageSize - 1) / pageSize;
            // 确定当前页码
            if (curIndex <= 0) {
                curIndex = 1L;
            }
            if (curIndex > pagesCount) {
                curIndex = pagesCount;
            }
            // 确定下一页码
            nextIndex = curIndex + 1;
            if (nextIndex > pagesCount) {
                nextIndex = pagesCount;
            }
            // 确定上一页码
            preIndex = curIndex - 1;
            if (preIndex <= 0) {
                preIndex = 1L;
            }
        } else {
            this.preIndex = 1L;
            this.curIndex = 1L;
            this.nextIndex = 1L;
            this.pageSize = 0L;
            this.pagesCount = 1L;
        }
    }

    /**
     * 设置总记录数
     *
     * @param rowsCount
     */
    public void setRowsCount(Long rowsCount) {
        updateInfo(curIndex, pageSize, rowsCount);
    }

    /**
     * 设置总页数
     *
     * @param pagesCount
     */
    public void setPagesCount(Long pagesCount) {
        this.pagesCount = pagesCount;
    }
}
