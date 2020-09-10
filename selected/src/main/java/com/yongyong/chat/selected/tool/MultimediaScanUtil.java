package com.yongyong.chat.selected.tool;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.yongyong.chat.selected.MultimediaSelectedType;
import com.yongyong.chat.selected.R;
import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;
import com.yongyong.chat.selected.entity.MultimediaSelectedFolderEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author 王永勇
 * 本地媒体数据库查询工具类
 * @// TODO: 2020/7/8
 */
public class MultimediaScanUtil {

    /**
     *
     */
    private Context mContext;

    /**
     *
     */
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;

    /**
     * 媒体类型
     */
    private MultimediaSelectedType multimediaType = MultimediaSelectedType.All;

    /**
     *
     */
    private boolean isGif = true;

    /**
     * 视频最大时长
     */
    private long maxDuration = 15;

    /**
     * 视频最小时长
     */
    private long minDuration = 3;

    /**
     * 查询URI
     */
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");

    /**
     * 排序
     */
    private static final String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";
    private static final String COLUMN_BUCKET_ID = "bucket_id";
    private static final String COLUMN_BUCKET_DISPLAY_NAME = "bucket_display_name";

    /**
     *
     * @param context
     */
    public MultimediaScanUtil(Context context){
        mContext = context;
    }

    /**
     *
     * @param context
     * @return
     */
    public static MultimediaScanUtil create(Context context) {
        return new MultimediaScanUtil(context);
    }

    /**
     *
     * @param multimediaType
     * @return
     */
    public MultimediaScanUtil setMultimediaType(MultimediaSelectedType multimediaType) {
        this.multimediaType = multimediaType;
        return this;
    }

    /**
     *
     * @param gif
     * @return
     */
    public MultimediaScanUtil setGif(boolean gif) {
        isGif = gif;
        return this;
    }

    /**
     *
     * @param maxDuration
     * @return
     */
    public MultimediaScanUtil setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
        return this;
    }

    /**
     *
     * @param minDuration
     * @return
     */
    public MultimediaScanUtil setMinDuration(long minDuration) {
        this.minDuration = minDuration;
        return this;
    }

    /**
     * 媒体文件数据库字段
     */
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,//路径
            MediaStore.MediaColumns.MIME_TYPE,//文件类型
            MediaStore.MediaColumns.WIDTH,//宽度
            MediaStore.MediaColumns.HEIGHT,//高度
            MediaStore.MediaColumns.SIZE,//大小
            MediaStore.MediaColumns.DURATION};//时长

    private static final String[] PROJECTION_29 = {
            MediaStore.Files.FileColumns._ID,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE};

    private static final String[] PROJECTION_LOW = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            "COUNT(*) AS count"};

    /**
     *
     * @param completeListener
     */
    public void loadFolder(final OnMultimediaFolderCompleteListener completeListener) {
        if (completeListener == null)
            return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor data = mContext.getContentResolver().query(QUERY_URI, isAndroidQ ? PROJECTION_29 : PROJECTION_LOW, getMultimediaType(), getArgs(), ORDER_BY);
                try {
                    if (data != null) {
                        List<MultimediaSelectedFolderEntity> folderEntityList = new ArrayList<>();
                        if (data.getCount() > 0) {
                            int totalCount = 0;
                            if (isAndroidQ) {
                                Map<Long, Long> countMap = new HashMap<>();
                                while (data.moveToNext()) {
                                    long bucketId = data.getLong(data.getColumnIndex(COLUMN_BUCKET_ID));
                                    Long newCount = countMap.get(bucketId);
                                    if (newCount == null) {
                                        newCount = 1L;
                                    } else {
                                        newCount++;
                                    }
                                    countMap.put(bucketId, newCount);
                                }

                                if (data.moveToFirst()) {
                                    Set<Long> hashSet = new HashSet<>();
                                    do {
                                        long bucketId = data.getLong(data.getColumnIndex(COLUMN_BUCKET_ID));
                                        if (hashSet.contains(bucketId)) {
                                            continue;
                                        }
                                        MultimediaSelectedFolderEntity mediaFolder = new MultimediaSelectedFolderEntity();
                                        mediaFolder.setBucketId(bucketId);
                                        String bucketDisplayName = data.getString(
                                                data.getColumnIndex(COLUMN_BUCKET_DISPLAY_NAME));
                                        long size = countMap.get(bucketId);
                                        long id = data.getLong(data.getColumnIndex(MediaStore.Files.FileColumns._ID));
                                        mediaFolder.setFolder(bucketDisplayName);
                                        mediaFolder.setNum(toInt(size));
                                        mediaFolder.setPath(getRealPathAndroid_Q(id));
                                        folderEntityList.add(mediaFolder);
                                        hashSet.add(bucketId);
                                        totalCount += size;
                                    } while (data.moveToNext());
                                }

                            } else {
                                data.moveToFirst();
                                do {
                                    MultimediaSelectedFolderEntity folderEntity = new MultimediaSelectedFolderEntity();
                                    long bucketId = data.getLong(data.getColumnIndex(COLUMN_BUCKET_ID));
                                    String bucketDisplayName = data.getString(data.getColumnIndex(COLUMN_BUCKET_DISPLAY_NAME));
                                    int size = data.getInt(data.getColumnIndex("count"));
                                    String url = data.getString(data.getColumnIndex(MediaStore.MediaColumns.DATA));
                                    folderEntity.setBucketId(bucketId);
                                    folderEntity.setPath(url);
                                    folderEntity.setFolder(bucketDisplayName);
                                    folderEntity.setNum(size);
                                    folderEntityList.add(folderEntity);
                                    totalCount += size;
                                } while (data.moveToNext());
                            }

                            Collections.sort(folderEntityList);

                            // 全部
                            MultimediaSelectedFolderEntity allMediaFolder = new MultimediaSelectedFolderEntity();
                            allMediaFolder.setNum(totalCount);
                            allMediaFolder.setChecked(true);
                            if (data.moveToFirst()) {
                                String firstUrl = isAndroidQ ? getFirstUri(data) : getFirstUrl(data);
                                allMediaFolder.setPath(firstUrl);
                            }
                            allMediaFolder.setFolder(mContext.getString(R.string.multimedia_selected_folder_all));
                            folderEntityList.add(0, allMediaFolder);

                            completeListener.onComplete(folderEntityList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (data != null && !data.isClosed()) {
                        data.close();
                    }
                }
            }
        }).start();
    }

    /**
     *
     * @param bucketId
     * @param page
     * @param listener
     */
    public void loadMultimedia(long bucketId, int page, OnMultimediaCompleteListener listener) {
        if (listener == null)
            return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor data = null;
                try {
                    String orderBy = page == -1 ? MediaStore.Files.FileColumns._ID + " DESC" : MediaStore.Files.FileColumns._ID + " DESC limit " + 60 + " offset " + (page - 1) * 60;
                    data = mContext.getContentResolver().query(QUERY_URI, PROJECTION, getMultimediaType(bucketId), getArgs(bucketId), orderBy);
                    if (data != null) {
                        List<MultimediaSelectedEntity> entityList = new ArrayList<>();
                        if (data.getCount() > 0) {
                            data.moveToFirst();
                            do {
                                long id = data.getLong
                                        (data.getColumnIndexOrThrow(PROJECTION[0]));

                                String absolutePath = data.getString
                                        (data.getColumnIndexOrThrow(PROJECTION[1]));

                                String url = isAndroidQ ? getRealPathAndroid_Q(id) : absolutePath;

                                String mimeType = data.getString
                                        (data.getColumnIndexOrThrow(PROJECTION[2]));
                                mimeType = TextUtils.isEmpty(mimeType) ? "image/jpeg" : mimeType;

                                int width = data.getInt
                                        (data.getColumnIndexOrThrow(PROJECTION[3]));

                                int height = data.getInt
                                        (data.getColumnIndexOrThrow(PROJECTION[4]));

                                long duration = data.getLong
                                        (data.getColumnIndexOrThrow(PROJECTION[6]));

                                long size = data.getLong
                                        (data.getColumnIndexOrThrow(PROJECTION[5]));

                         /*       String folderName = data.getString
                                        (data.getColumnIndexOrThrow(PROJECTION[7]));

                                String fileName = data.getString
                                        (data.getColumnIndexOrThrow(PROJECTION[8]));

                                long bucket_id = data.getLong
                                        (data.getColumnIndexOrThrow(PROJECTION[9]));*/

                                /*if (PictureMimeType.isHasVideo(mimeType)) {
                                    if (config.videoMinSecond > 0 && duration < config.videoMinSecond) {
                                        // If you set the minimum number of seconds of video to display
                                        continue;
                                    }
                                    if (config.videoMaxSecond > 0 && duration > config.videoMaxSecond) {
                                        // If you set the maximum number of seconds of video to display
                                        continue;
                                    }
                                    if (duration == 0) {
                                        //If the length is 0, the corrupted video is processed and filtered out
                                        continue;
                                    }
                                    if (size <= 0) {
                                        // The video size is 0 to filter out
                                        continue;
                                    }
                                }*/

                                MultimediaSelectedEntity entity = new MultimediaSelectedEntity();
                                entity.setPath(url);
                                entity.setDuration(duration);
                                entity.setHeight(height);
                                entity.setWidth(width);
                                entityList.add(entity);
                                entity.setMimeType(mimeType);
                                entity.setSize(size);
                            } while (data.moveToNext());
                        }
                        listener.onMultimediaComplete(bucketId,entityList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (data != null && !data.isClosed()) {
                        data.close();
                    }
                }
            }
        }).start();
    }

    /* 内部API */

    /**
     *
     * @return
     */
    private String getMultimediaType() {

        if (multimediaType == MultimediaSelectedType.IMAGE){
            if (isAndroidQ) {
                return isGif ? MediaStore.Files.FileColumns.MEDIA_TYPE + "=? "
                        + " AND " + MediaStore.MediaColumns.SIZE + ">0" :  MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                        + " AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/gif' AND "+MediaStore.MediaColumns.MIME_TYPE + "!='image/*'" + " AND " + MediaStore.MediaColumns.SIZE + ">0";
            }
            return isGif ? "( + MediaStore.Files.FileColumns.MEDIA_TYPE + =? )" +
                    "            + AND + MediaStore.MediaColumns.SIZE + >0) + GROUP_BY_BUCKET_Id" : "( + MediaStore.Files.FileColumns.MEDIA_TYPE + =?" +
                    "            + AND + MediaStore.MediaColumns.MIME_TYPE + NOT_GIF + ) AND + MediaStore.MediaColumns.SIZE + >0) + GROUP_BY_BUCKET_Id";
        }else if (multimediaType == MultimediaSelectedType.VIDEO){
            if (isAndroidQ) {
                return MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                        + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                        + " AND " + getDuration();
            }
            return "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + ") AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + " AND " + getDuration() + ") GROUP BY (bucket_id";
        }else {
            if (isAndroidQ) {
                return  "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                        + (isGif ? "" : " AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/gif' AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/*'")
                        + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + getDuration() + ") AND " + MediaStore.MediaColumns.SIZE + ">0";
            }else {
                return  "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                        + (isGif ? "" : " AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/gif' AND + MediaStore.MediaColumns.MIME_TYPE + !='image/*'")
                        + " OR " + (MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + getDuration()) + ")" + " AND " + MediaStore.MediaColumns.SIZE + ">0)" + "GROUP BY (bucket_id";
            }
        }
    }

    /**
     *
     * @param bucketId
     * @return
     */
    private String getMultimediaType(long bucketId) {
        String durationCondition = getDuration();
        if (multimediaType == MultimediaSelectedType.IMAGE){
            if (bucketId == -1) {
                return "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                        + (isGif ? "" : " AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/gif' AND + MediaStore.MediaColumns.MIME_TYPE + !='image/*'")
                        + ") AND " + MediaStore.MediaColumns.SIZE + ">0";
            }
            return "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + (isGif ? "" : " AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/gif' AND + MediaStore.MediaColumns.MIME_TYPE + !='image/*'")
                    + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + durationCondition + ") AND " + COLUMN_BUCKET_ID + "=? AND " + MediaStore.MediaColumns.SIZE + ">0";
        }else if (multimediaType == MultimediaSelectedType.VIDEO){
            if (bucketId == -1) {
                return "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + durationCondition + ") AND " + MediaStore.MediaColumns.SIZE + ">0";
            }
            return "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + durationCondition + ") AND " + COLUMN_BUCKET_ID + "=? AND " + MediaStore.MediaColumns.SIZE + ">0";
        }else {
            if (bucketId == -1) {
                // ofAll
                return "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                        + (isGif ? "" : " AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/gif' AND + MediaStore.MediaColumns.MIME_TYPE + !='image/*'")
                        + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + durationCondition + ") AND " + MediaStore.MediaColumns.SIZE + ">0";
            }
            // Gets the specified album directory
            return "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + (isGif ? "" : " AND " + MediaStore.MediaColumns.MIME_TYPE + "!='image/gif' AND + MediaStore.MediaColumns.MIME_TYPE + !='image/*'")
                    + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + durationCondition + ") AND " + COLUMN_BUCKET_ID + "=? AND " + MediaStore.MediaColumns.SIZE + ">0";
        }
    }

    /**
     *
     * @return
     */
    private String[] getArgs() {
        if (multimediaType == MultimediaSelectedType.IMAGE){
            return new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)};
        }else if (multimediaType == MultimediaSelectedType.VIDEO){
            return new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)};
        }else {
            return new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)};
        }
    }

    /**
     *
     * @param bucketId
     * @return
     */
    private String[] getArgs(long bucketId) {
        if (multimediaType == MultimediaSelectedType.IMAGE){
            return bucketId == -1 ? new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)} : new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE), toString(bucketId)};
        }else if (multimediaType == MultimediaSelectedType.VIDEO){
            return bucketId == -1 ? new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)} : new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),toString(bucketId)};
        }else {
            if (bucketId == -1) {
                // ofAll
                return new String[]{
                        String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
                        String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
                };
            }
            //  Gets the specified album directory
            return new String[]{
                    String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
                    String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
                    toString(bucketId)
            };
        }
    }

    /**
     *
     * @return
     */
    private String getDuration() {
        long max = maxDuration == 0 ? Long.MAX_VALUE : maxDuration;
        if (maxDuration != 0) {
            max = Math.min(max, maxDuration);
        }
        return String.format(Locale.CHINA, "%d <%s " + MediaStore.MediaColumns.DURATION + " and " + MediaStore.MediaColumns.DURATION + " <= %d",
                Math.max(minDuration, minDuration),
                Math.max(minDuration, minDuration) == 0 ? "" : "=",
                max);
    }

    /**
     * Get cover uri
     *
     * @param cursor
     * @return
     */
    private String getFirstUri(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
        return getRealPathAndroid_Q(id);
    }

    /**
     * Get cover url
     *
     * @param cursor
     * @return
     */
    private String getFirstUrl(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
    }

    /**
     *
     * @param id
     * @return
     */
    private String getRealPathAndroid_Q(long id) {
        return QUERY_URI.buildUpon().appendPath(toString(id)).build().toString();
    }

    /**
     *
     * @param o
     * @return
     */
    private String toString(Object o){
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }
        return value;
    }


    /**
     *
     * @param o
     * @return
     */
    private int toInt(Object o) {
        int value = 0;
        if (o == null) {
            return value;
        }
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Integer.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Integer.valueOf(s);
            }
        } catch (Exception e) {
        }

        return value;
    }

    /**
     * 回调接口
     */
    public interface OnMultimediaFolderCompleteListener {

        /**
         *
         * @param list
         */
        void onComplete(List<MultimediaSelectedFolderEntity> list);
    }

    /**
     *
     */
    public interface OnMultimediaCompleteListener {

        /**
         *
         * @param list
         */
        void onMultimediaComplete(long bucketId,List<MultimediaSelectedEntity> list);
    }
}
