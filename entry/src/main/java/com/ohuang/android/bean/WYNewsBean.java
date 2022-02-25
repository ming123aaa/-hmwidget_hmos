package com.ohuang.android.bean;

import java.io.Serializable;
import java.util.List;


public class WYNewsBean implements Serializable {

    /**
     * code : 200
     * message : 成功!
     * result : [{"path":"https://www.163.com/dy/article/G1OBC8LO0514BCL4.html","image":"http://dingyue.ws.126.net/2021/0201/b63f2e50j00qntwfh0020c000hs00npg.jpg?imageView&thumbnail=140y88&quality=85","title":"被指偷拿半卷卫生纸 63岁女洗碗工服药自杀 酒店回应","passtime":"2021-02-02 10:00:51"},{"path":"https://www.163.com/dy/article/G1O1Q9Q2053469M5.html","image":"http://cms-bucket.ws.126.net/2021/0201/9860dbd3p00qntxlo00iqc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"警方通报＂19岁女大学生学车后失联＂:已遇害 全力侦办","passtime":"2021-02-02 10:00:51"},{"path":"https://news.163.com/21/0201/10/G1OAFSJR0001899O.html","image":"http://cms-bucket.ws.126.net/2021/0201/9c211c79p00qnu1ff00h8c000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"缅军方接管政权 副总统敏瑞任代总统 白宫紧急回应","passtime":"2021-02-02 10:00:51"},{"path":"https://www.163.com/dy/article/G1QQ2KIT051482MP.html","image":"http://bjnewsrec-cv.ws.126.net/little61377fe4179p00qnvo3n00ded200u000fwg00it009y.jpg?imageView&thumbnail=140y88&quality=85","title":"孙小果母亲讲述经历：＂我老公从没有没办成的事儿＂","passtime":"2021-02-02 10:00:50"},{"path":"https://www.163.com/dy/article/G1QLGSGT0514BE2Q.html","image":"http://dingyue.ws.126.net/2021/0202/25b0f9dcj00qnvkdk002fc000u000k3m.jpg?imageView&thumbnail=140y88&quality=85","title":"安徽名酒产地地下水告急：打井五六百米才出水","passtime":"2021-02-02 10:00:50"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }


    public static class ResultBean implements Serializable {
        /**
         * path : https://www.163.com/dy/article/G1OBC8LO0514BCL4.html
         * image : http://dingyue.ws.126.net/2021/0201/b63f2e50j00qntwfh0020c000hs00npg.jpg?imageView&thumbnail=140y88&quality=85
         * title : 被指偷拿半卷卫生纸 63岁女洗碗工服药自杀 酒店回应
         * passtime : 2021-02-02 10:00:51
         */

        private String path;
        private String image;
        private String title;
        private String passtime;


        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }
    }
}
