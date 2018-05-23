
package com.android.base.common.database;

/**
 * DAO常量
 * 
 * @author ZhaoRuYang
 */
public interface LocalDaoConst {
    /**
     * 数据库名称
     */
    String DB_NAME = "local_file.db";

    /**
     * 数据库版本号
     */
    int DB_VERSION = 1;

    /**
     * 本地文件表
     * 
     * @author ZhaoRuYang
     * @date 2015年10月19日
     */
    public interface LocalFile {

        String TABLE_NAME = "LocalFile";

        String PATH = "Path";

        String PARENT_DIR="ParentDir";

        String NAME = "Name";

        String SIZE = "Size";

        String TOUCH_TIME = "TouchTime";

        String FILE_TYPE = "FileType";

        String tableCreate = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME // 创建表
                + "("
                // + "_id integer primary key autoincrement," // id
                + PATH + " VARCHAR PRIMARY KEY, " // 路径
                + PARENT_DIR + " VARCHAR, " // 父目录路径
                + NAME + " VARCHAR, " // 名称
                + SIZE + " INTEGER DEFAULT 0 , " // 大小
                + TOUCH_TIME + " VARCHAR , " // 时间
                + FILE_TYPE + " INTEGER DEFAULT 0"// 文件类型
                + ")";

        String tableInsert = "REPLACE INTO " + TABLE_NAME // 插入表
                + "(" //
                + PATH + "," // 插入路径
                + PARENT_DIR + "," // 插入父路径
                + NAME + "," //
                + SIZE + "," //
                + TOUCH_TIME + "," //
                + FILE_TYPE // 文件类型
                + ") VALUES(?, ?,?,?,?,?)";

        String tableSelect = "select * from " + TABLE_NAME //
                + " where "//
                + FILE_TYPE//
                + "=?";

        String tableSelectByDir = "select * from " + TABLE_NAME //
                + " where "//
                + FILE_TYPE//
                + "=? and "//
                + PATH //
                + " like ?";
        
        String tableSelectAllByDir = "select * from " + TABLE_NAME //
                + " where "//
                + FILE_TYPE//
                + " IN ( ?, ? ) and "//
                + PARENT_DIR //
                + " =?";
        //清空表
        String tableClear = "delete from "+ TABLE_NAME;
    }

    /**
     * 定义文件类型FILETYPE
     * 
     * @author ZhaoRuYang
     * @date 2015年10月19日
     */
    public interface FileType {

        int NONE = 0;

        int MUSIC = 1;

        int PIC = 2;

        int DIR_BASE = 10000;

        int DIR_MUSIC = DIR_BASE + MUSIC;

        int DIR_PIC = DIR_BASE + PIC;
    }

}
