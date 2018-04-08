package com.trump.auction.common.util.page;

import com.github.pagehelper.PageInfo;
import com.trump.auction.common.util.mapping.BeanMapper;

import java.util.List;

/**
 * 分页工具类
 * @author Owen.Yuan 2017/6/15.
 */
public abstract class PageUtils {

    public static <S, D> PageInfo<D> getPageInfo(List<S> source, Class<D> destinationClass, BeanMapper beanMapper) {
        PageInfo<S> srcPage = getPageInfo( source );
        PageInfo<D> destPage = beanMapper.map( srcPage, PageInfo.class );
        destPage.setList(beanMapper.mapAsList(srcPage.getList(), destinationClass));
        return destPage;
    }

    public static <S> PageInfo<S> getPageInfo(List<S> source) {
        return new PageInfo<>( source );
    }

    public static <S, D> Paging<D> page(List<S> source, Class<D> destinationClass, BeanMapper beanMapper) {
        PageInfo<S> srcPage = getPageInfo( source );
        Paging<D> result = new Paging<>();
        copy( srcPage, result );
        result.setList(beanMapper.mapAsList(srcPage.getList(), destinationClass));
        return result;
    }

    public static <S> Paging<S> page(List<S> source) {
        PageInfo<S> srcPage = getPageInfo( source );
        Paging<S> result = new Paging<>();
        copy( srcPage, result );
        return result;
    }

    private static void copy(PageInfo src, Paging dest) {
        dest.setPageNum( src.getPageNum() );
        dest.setPageSize( src.getPageSize() );
        dest.setSize( src.getSize() );
        dest.setOrderBy( src.getOrderBy() );
        dest.setStartRow( src.getStartRow() );
        dest.setEndRow( src.getEndRow() );
        dest.setTotal( src.getTotal() );
        dest.setPages( src.getPages() );
        dest.setList( src.getList() );
        dest.setFirstPage( src.getFirstPage() );
        dest.setPrePage( src.getPrePage() );
        dest.setNextPage( src.getNextPage() );
        dest.setLastPage( src.getLastPage() );
        dest.setIsFirstPage( src.isIsFirstPage() );
        dest.setIsLastPage( src.isIsLastPage() );
        dest.setHasPreviousPage( src.isHasPreviousPage() );
        dest.setHasNextPage( src.isHasNextPage() );
        dest.setNavigatePages( src.getNavigatePages() );
        dest.setNavigatepageNums( src.getNavigatepageNums() );
    }

    public static <S, D> Paging<D> from(Paging<S> source, Class<D> destinationClass, BeanMapper beanMapper) {
        if (source == null) {
            return null;
        }
        Paging<D> result = beanMapper.map(source, Paging.class);
        result.setList(beanMapper.mapAsList(source.getList(), destinationClass));
        return result;
    }
}
