package com.ligh.example.redisdemo;

public interface IZSetTuple extends Comparable<IZSetTuple> {

    /**
     * 成员名称
     *
     * @return
     */
    String getMember();

    /**
     * 成员成绩
     *
     * @return
     */
    long getScore();
}
