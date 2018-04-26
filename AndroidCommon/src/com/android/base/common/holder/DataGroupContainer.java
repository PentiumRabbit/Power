package com.android.base.common.holder;

import java.util.ArrayList;
import java.util.List;

/**
 * ZhaoRuYang
 * time : 17-9-12
 */

public class DataGroupContainer extends DataContainer {

    private final static int TYPE_NONE = 0;
    private int size = 0;
    private List<DataContainer> childs = new ArrayList<>();
    private int endType = TYPE_NONE;
    private int headType = TYPE_NONE;

    public DataGroupContainer(int typeID) {
        super(typeID);
    }


    @Override
    public int size() {
        return size;
    }

    public void setHeadType(int type) {
        if (headType == TYPE_NONE) {
            size++;
        }
        this.headType = type;

    }

    public void setEndType(int type) {
        if (endType == TYPE_NONE) {
            size++;
        }

        endType = type;

    }

    public List<DataContainer> getChildList() {
        return childs;
    }


    public List<DataContainer> toList() {

        if (size() == 0) {
            return null;
        }

        List<DataContainer> newViewItems = new ArrayList<>();
        // 如果是第一个
        if (headType != TYPE_NONE) {
            DataContainer newItem = new DataContainer(headType);
            newItem.setData(this.getData());
            newViewItems.add(newItem);
        }

        newViewItems.addAll(childs);


        // 如果是最后一个
        if (endType != TYPE_NONE) {
            DataContainer newItem = new DataContainer(endType);
            newItem.setData(this.getData());
            newViewItems.add(newItem);
        }

        return newViewItems;

    }

    public int getChildListSize() {
        return childs.size();
    }

    public void add(DataContainer item) {
        if (item == null) {
            return;
        }
        size += item.size();

        item.setParent(this);
        childs.add(item);
    }

    public void insert(int pos, DataContainer item) {
        if (item == null) {
            return;
        }
        size += item.size();

        item.setParent(this);
        childs.add(pos, item);
    }


    public DataContainer getLastItem() {
        if (childs == null || childs.size() == 0) {
            return null;
        }
        return childs.get(childs.size() - 1);
    }

    public void merge(DataGroupContainer item) {
        List<DataContainer> allChild = item.getChildList();
        childs.addAll(allChild);
        size += allChild.size();
        // TODO: 2017/6/13 指定父类
    }


    public void insertChildList(int position, DataContainer item) {
        childs.add(position, item);
        size += item.size();
        item.setParent(this);
    }


    @Override
    public DataContainer get(int position) {
        if (position >= size) {
            throw new IndexOutOfBoundsException("越界了　：　" + position);
        }

        int offsetPosition = position;


        // 如果是第一个
        if (headType != TYPE_NONE) {
            if (offsetPosition == 0) {
                setTypeID(headType);
                return this;
            } else {
                // 去除头位置，得出新偏移量
                offsetPosition--;
            }
        }

        // 如果是在子孩子中
        for (DataContainer viewItem : childs) {
            int size = viewItem.size();
            if (offsetPosition < size) {
                return viewItem.get(offsetPosition);
            } else {
                offsetPosition -= size;
            }
        }


        // 如果是最后一个
        if (endType != TYPE_NONE && position == size - 1) {
            setTypeID(endType);
            return this;
        }

        return null;

    }

}
