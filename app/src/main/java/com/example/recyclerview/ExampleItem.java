package com.example.recyclerview;

public class ExampleItem {
        private String mPercentage;
        private String mText1, mAttendance, mBunked, mAttended;


        public ExampleItem(String percentage, String text1, String minA, String bunked, String attended) {
            mPercentage = percentage;
            mText1 = text1;
            mAttendance = minA;
            mBunked = bunked;
            mAttended = attended;
        }
        public void changeText1(String text) {
            mText1 = text;
        }

        public String getmPercentage() {
            return mPercentage;
        }

        public void setmPercentage(String percentage) {
            this.mPercentage = percentage;
        }

        public String getmText1() {
            return mText1;
        }

        public void setmText1(String text1) {
            this.mText1 = text1;
        }

        public String getmAttendance() {
        return mAttendance;
    }

        public void setmAttendance(String minA) {
        this.mAttendance = minA;
    }
    public String getmBunked() {
        return mBunked;
    }

    public void setmBunked(String bunked) {
        this.mBunked = bunked;
    }

    public String getmAttended() {
        return mAttended;
    }

    public void setmAttended(String attended) {
        this.mAttended = attended;
    }
}

