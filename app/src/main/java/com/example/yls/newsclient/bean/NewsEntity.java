package com.example.yls.newsclient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yls on 2017/6/27.
 */

public class NewsEntity implements Serializable{


    /**
     * postid : PHOT2527B000100A
     * hasCover : false
     * hasHead : 1
     * replyCount : 4396
     * hasImg : 1
     * digest :
     * hasIcon : false
     * docid : 9IG74V5H00963VRO_CO0T438QbjjikeupdateDoc
     * title : 郑州一商场大火后坍塌 商户损失上亿
     * order : 1
     * priority : 354
     * lmodify : 2017-06-28 10:35:19
     * boardid : photoview_bbs
     * ads : [{"title":"委内瑞拉反政府游行 民众趁乱洗劫超市","skipID":"00AO0001|2263273","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/ed01271290864777a659a2f94d7f233220170628103427.jpeg","subtitle":"","skipType":"photoset","url":"00AO0001|2263273"},{"title":"解除武装 哥武领导人与总统握手言和","skipID":"00AO0001|2263253","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/f15c7fc7462e416d9dc8a7997ae4326d20170628093227.jpeg","subtitle":"","skipType":"photoset","url":"00AO0001|2263253"},{"title":"西安持续高温 大学生在足球场过夜","skipID":"00AP0001|2263232","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/619478fbd57b48208e74981e950c37c220170628082751.jpeg","subtitle":"","skipType":"photoset","url":"00AP0001|2263232"},{"title":"七一临近 香港维港两岸\"升起\"国旗区旗","skipID":"00AN0001|2263233","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/dfb19667874644d883213c47bdf9002820170628082619.jpeg","subtitle":"","skipType":"photoset","url":"00AN0001|2263233"},{"title":"美国纽约发生地铁脱轨事故 致34人受伤","skipID":"00AO0001|2263229","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/4947b7da071e46399cf953bd314eaf1f20170628081222.jpeg","subtitle":"","skipType":"photoset","url":"00AO0001|2263229"}]
     * photosetID : 00AP0001|2263275
     * imgsum : 5
     * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
     * template : normal1
     * votecount : 4069
     * skipID : 00AP0001|2263275
     * alias : Top News
     * skipType : photoset
     * cid : C1348646712614
     * hasAD : 1
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/f497d572b03649a18c788ddbac6449c920170628103314.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/2cc413a39f4244f99617b63dea3a12a120170628103313.jpeg"}]
     * source : 网易综合
     * ename : androidnews
     * tname : 头条
     * imgsrc : http://cms-bucket.nosdn.127.net/78b333b8e7b14e5b9772f04be251077820170628103313.jpeg
     * ptime : 2017-06-28 10:33:27
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        private String postid;
        private boolean hasCover;
        private int hasHead;
        private int replyCount;
        private int hasImg;
        private String digest;
        private boolean hasIcon;
        private String docid;
        private String title;
        private int order;
        private int priority;
        private String lmodify;
        private String boardid;
        private String photosetID;
        private int imgsum;
        private String topic_background;
        private String template;
        private int votecount;
        private String skipID;
        private String alias;
        private String skipType;
        private String cid;
        private int hasAD;
        private String source;
        private String ename;
        private String tname;
        private String imgsrc;
        private String ptime;
        private String url;
        private String url_3w;

        public String getUrl_3w() {
            return url_3w;
        }

        public void setUrl_3w(String url_3w) {
            this.url_3w = url_3w;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * title : 委内瑞拉反政府游行 民众趁乱洗劫超市
         * skipID : 00AO0001|2263273
         * tag : photoset
         * imgsrc : http://cms-bucket.nosdn.127.net/ed01271290864777a659a2f94d7f233220170628103427.jpeg
         * subtitle :
         * skipType : photoset
         * url : 00AO0001|2263273
         */

        private List<AdsBean> ads;
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/f497d572b03649a18c788ddbac6449c920170628103314.jpeg
         */

        private List<ImgextraBean> imgextra;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getHasImg() {
            return hasImg;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getPhotosetID() {
            return photosetID;
        }

        public void setPhotosetID(String photosetID) {
            this.photosetID = photosetID;
        }

        public int getImgsum() {
            return imgsum;
        }

        public void setImgsum(int imgsum) {
            this.imgsum = imgsum;
        }

        public String getTopic_background() {
            return topic_background;
        }

        public void setTopic_background(String topic_background) {
            this.topic_background = topic_background;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getVotecount() {
            return votecount;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getHasAD() {
            return hasAD;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public List<ImgextraBean> getImgextra() {
            return imgextra;
        }

        public void setImgextra(List<ImgextraBean> imgextra) {
            this.imgextra = imgextra;
        }

        public static class AdsBean implements Serializable{
            private String title;
            private String skipID;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String skipType;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSkipID() {
                return skipID;
            }

            public void setSkipID(String skipID) {
                this.skipID = skipID;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getSkipType() {
                return skipType;
            }

            public void setSkipType(String skipType) {
                this.skipType = skipType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ImgextraBean implements Serializable{
            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }
        }
    }
}
