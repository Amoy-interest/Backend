package com.example.amoy_interest.utils;

import java.util.Date;

public class HotRank {

    private static long epoch_seconds(Date pubtimes) {
        return pubtimes.getTime() / 1000 - 1134028003;
    }

    /******
     * 输入点赞,踩,返回差值
     * @param ups
     * @param downs
     * @return
     */
    private static long score(long ups, long downs) {
        return ups - downs;
    }

    /******
     * 计算热度值,输入点赞,踩和话题创建时间,返回热度值
     * @param ups 话题点赞的数量
     * @param downs 话题踩的数量
     * @param pubtimes 话题发布时间
     * @return
     */
    public static double getHotVal(long ups, long downs, Date pubtimes) {
        double hotrank = 0;
        long seconds = 0;
        long scorecount = 0;
        double order = 0;
        long signcount = 0;

        scorecount = score(ups, downs);
        seconds = epoch_seconds(pubtimes);

        order = Math.log10(Math.max(Math.abs(scorecount), 1));
        signcount = (long) Math.signum(scorecount);

        hotrank = order + (signcount * seconds) / 45000;

        return hotrank;
    }
}
