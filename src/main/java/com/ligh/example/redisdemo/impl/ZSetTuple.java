package com.ligh.example.redisdemo.impl;


import com.ligh.example.redisdemo.IZSetTuple;

public class ZSetTuple implements IZSetTuple {
    private final String member;
    private final long score;

    public ZSetTuple(String member, long score) {
        this.member = member;
        this.score = score;
    }

    @Override
    public String getMember() {
        return member;
    }

    @Override
    public long getScore() {
        return score;
    }

    @Override
    public int compareTo(IZSetTuple o) {
        if (o == null) {
            return 1;
        }
        if (o.getScore() == score) {
            return 0;
        }
        return score > o.getScore() ? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof ZSetTuple)) {
            return false;
        }
        ZSetTuple other = (ZSetTuple) obj;
        if (this.score != other.getScore()) {
            return false;
        }
        if (this.member != null) {
            return this.member.equals(other.getMember());
        }
        return null == other.getMember();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((Long) this.score).hashCode();
        result = 31 * result + (this.member == null ? 0 : this.member.hashCode());
        return result;
    }
}
