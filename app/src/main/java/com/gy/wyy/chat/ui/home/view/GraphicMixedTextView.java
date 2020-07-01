package com.gy.wyy.chat.ui.home.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 */
public class GraphicMixedTextView extends AppCompatTextView {

    /**
     *
     */
    private ArrayList<String> urlList = new ArrayList<>();

    private String imageDefaultString = "img";

    public GraphicMixedTextView(Context context) {
        super(context);
        initView();
    }

    public GraphicMixedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GraphicMixedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        try {
            JSONObject jsonObject = new JSONObject(getJson());
            String content = jsonObject.getString("body");
            JSONArray jsonArray = jsonObject.getJSONArray("img");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String url = "<img src="+json.getString("src")+">";
                String str = "<!--IMG#"+i+"-->";
                String [] strings = content.split(str);
                if (strings.length > 1){
                    content = strings[0] + url + strings[1];
                }else {
                    content = strings[0] + url;
                }
            }
            stringToHtml(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param html
     */
    public void stringToHtml(String html){
        if (TextUtils.isEmpty(html))
            return;

        setText(Html.fromHtml(html,new GraphicMixedImageGetter(),new GraphicMixedTagHandler()));
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     *
     */
    public String getJson(){
        String str = "{\n" +
                "\t\"img\": [{\n" +
                "\t\t\"ref\": \"<!--IMG#0-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/aaa8ddf1fd2e704a20997910ce952ff1.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*336\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#1-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/1138a8a10ab3adb7789bad3a2961c56e.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*360\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#2-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/4776f8838e98a9baf76743d5a0ea09c9.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*360\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#3-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/91be0aa923e9a344d88d55db0213e5b9.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*360\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#4-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/ebeb93af9783b9b848e39e83ffe75731.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*360\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#5-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/676be3194a81992ea9c5c0cb638390a8.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*360\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#6-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/37941b1034c73eeb44027f895cf7370f.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"563*371\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#7-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/5cc79f12eb559959a36c7a9b09fd3d0a.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*360\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#8-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/b50bb061f61da5b1c285bbf0539c0c80.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"640*361\"\n" +
                "\t}, {\n" +
                "\t\t\"ref\": \"<!--IMG#9-->\",\n" +
                "\t\t\"src\": \"http://crawl.ws.126.net/img/c6a3d4b032ed78ce807a6cef457e983e.jpg\",\n" +
                "\t\t\"alt\": \"\",\n" +
                "\t\t\"pixel\": \"600*338\"\n" +
                "\t}],\n" +
                "\t\"body\": \"<p>　　01<\\/p><p>　　《清平乐》里，苗心禾曾经算计着把董秋和推到皇上的身边，这样可以帮自己说话，退一步说，也让张妼晗少一点机会算计徽柔。<\\/p><p>　　当时，曹丹姝特别不同意，说，人有的时候向着绿洲走，却进了荒漠，人生有些事，本就是无可奈何，只是要守住初心，不要变成自己曾经最讨厌的样子。<\\/p><!--IMG#0--><p>　　曹丹姝不想苗心禾事事和张妼晗争风吃醋，总是想着忍让，而且，她觉得，徽柔是皇上最疼爱的长女，即便他身边的人都说徽柔的坏话，可是皇上作为父亲，又怎么会不疼爱自己的女儿。<\\/p><p>　　<strong>苗心禾当时还是清醒的，她说，官家若不是糊涂，又怎会放着曹皇后这样的妻子不爱，却独宠张娘子呢。<\\/strong><\\/p><p>　　一句话真的是说中要害。<\\/p><p>　　只是，曹丹姝的性格，明明有算计的能力和算计的势力，可是偏偏不屑于做这样的事情。一生光明磊落，就是要用国母的本分严格要求自己。<\\/p><p>　　可是，没有曹丹姝助力的苗心禾，根本不是张妼晗的对手。<\\/p><!--IMG#1--><p>　　董秋和在七夕之试上落选，张妼晗推举的许静奴虽然也没有被选中，可是，自七夕之后，皇上对张妼晗却更加宠爱了，几乎夜夜都要宿在翔鸾阁。<\\/p><p>　　半年之久没去皇后那里，后来去了，吵了一架又走了。<\\/p><p>　　02<\\/p><p>　　张妼晗连续夭折了两个女儿，把仇恨都记在了曹丹姝和苗心禾头上。曹丹姝还想着，由着她闹吧，自己不去争，苗心禾温柔敦厚，根本算计不出。<\\/p><p>　　于是，中招的就是徽柔和弟弟最兴来了。<\\/p><p>　　整个后宫没有人染上瘟疫，可偏偏就是徽柔姐弟两个。而且是传染病，大家避之唯恐不及，所以，能真正在身旁的人并不多。<\\/p><!--IMG#2--><p>　　曹丹姝住到了仪凤阁照顾，赵祯更是疯了一样地怕保不住两个孩子。可是，张妼晗背后的一张网，夏悚在前朝，杨怀敏、贾婆婆在后宫，兰苕等待替补上位，如何肯让苗心禾的儿子长大成人继承皇位呢。<\\/p><p>　　最终，苗心禾的儿子没能保住，徽柔也只是侥幸活下来。<\\/p><p>　　后宫之中，看起来荣华富贵、喜气洋洋，可是背后的阴险狠辣，恐怕连身在其中的皇上妃嫔都不知道吧。<\\/p><p>　　<strong>张妼晗一心想着报仇，可是，却被杨怀敏、贾婆婆利用，自己坏了身子，只能提携兰苕上位。<\\/strong><\\/p><p>　　妼晗一直说，她最爱的就是官家，若是把官家对她的疼爱让出去半分，她是宁愿去死也舍不得的。<\\/p><!--IMG#3--><!--IMG#4--><!--IMG#5--><p>　　可是，她的爱既没有成全官家，也没有成全自己，所有的一往无前，也只是变成一把炮灰而已。<\\/p><p>　　03<\\/p><p>　　张妼晗的2个女儿被兰苕算计而死，苗心禾的儿子被张妼晗算计夭折，让苗心禾和曹丹姝都没了指望，可是，他们最终伤害的却是皇上。<\\/p><p>　　三皇子染了风寒，两天就没了，皇上的心里就不是滋味了。四公主精心地养着，可还是突然窒息而死，最兴来是皇上心尖上的肉，最后还是被夺了。<\\/p><p>　　宋代的君王之家本就有精神病史，赵祯在这重重打击之下，精神恍惚了，从此一蹶不振。<\\/p><!--IMG#6--><p>　　每天都觉得是自己愧对生母，未能尽孝道，所以上苍惩罚，报应在儿女身上。<\\/p><p>　　四公主殁了，赵祯辍朝7日不能理事，欧阳修愤愤不平，说于理不合，这是太过宠爱张妼晗了。<\\/p><p>　　赵祯在福宁殿不生火不取暖，每天不吃晚饭，想以此责罚自己为儿女祈福。但是，又夭折了最兴来。<\\/p><p>　　<strong>从此以后，赵祯的精神越来越差，有时神志不清，竟然20多天疯疯癫癫不能理事。严重的时候，更是想要自杀寻求解脱。<\\/strong><\\/p><p>　　赵家的精神病史在多位子女身上都曾发作，可以说是痛苦不堪。<\\/p><p>　　赵祯精神越来越差，张妼晗因病去世更是让他不堪一击，于是赵祯疯了一般地要追封张妼晗为皇后，而当时曹皇后还在位。<\\/p><!--IMG#7--><p>　　可是，谁能拦得住一位精神失常的人呢，于是这位一直是循规蹈矩、敬畏人言的皇帝不顾所有人的反对，非要追封，大臣们也是没有办法。<\\/p><p>　　04<\\/p><p>　　张妼晗生病时，贾婆婆、夏悚就想扶兰苕上位了，只是，曹丹姝背后的功勋集团也并不是傻子，不能再犯之前的错误了。<\\/p><p>　　于是，兰苕不可能成为第二个张妼晗了，曹丹姝堵死了这条路，推出了自己的侍女董秋和，秋和人品值得信赖，又出自中宫，这才让人放心。<\\/p><p>　　赵祯不能处理朝政的时候，都是曹丹姝全力支撑，若是没有这位能干的皇后，或许朝堂动荡，后果不堪设想。<\\/p><!--IMG#8--><p>　　宋真宗晚年卧病在床，幸好有刘太后一力支撑，宋仁宗晚年精神失常，也幸好有曹丹姝可以处理政事。<\\/p><p>　　只是皇室之中的精神病史真的是带来了很多伤害，赵宗实作为赵祯的养子，从小养在宫里，可是当赵祯有了亲生儿子之后，他出宫，却不肯再回来了。<\\/p><p>　　后来被迫无奈只好回来，还嘱咐自己的家人，宫里有了更合适的太子人选，我就回来了。由于压力过大，即位当天就精神恍惚，大哭大闹，幸好，曹太后和一群忠臣掌控了局面。<\\/p><p>　　<strong>即位之后就大病一场，是曹太后垂帘听政，后来好转亲政，但是4年之后也病逝了。儿子宋神宗，力主改革，可是当改革失败，他也爆发了精神疾病，最后去世。<\\/strong><\\/p><p>　　徽柔婚姻不幸，但是，这样的女子其实在当时并不罕见，曹丹姝嫁的第一个丈夫决议修仙，洞房当天就休妻跳窗逃跑了，可是，曹丹姝也没有因此抑郁。<\\/p><!--IMG#9--><p>　　可是徽柔却在婚姻失败之后，身边没有怀吉的陪伴就精神失常了。<\\/p><p>　　虽是帝王之家，但是也是如此可悲。<\\/p><p><!--viewpoint--><\\/p>\"\n" +
                "}";
        return str;
    }

    /**
     *
     */
    private class GraphicMixedTagHandler implements Html.TagHandler {

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (TextUtils.equals(imageDefaultString, tag.toLowerCase(Locale.getDefault()))) {
                int len = output.length();
                ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
                String imgURL = images[0].getSource();
                urlList.add(imgURL);
                //给图片添加点击事件
                output.setSpan(new GraphicMixedClickableImage(urlList.size() - 1), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     *
     */
    private class GraphicMixedImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {
            final LevelListDrawable drawable = new LevelListDrawable();
            Glide.with(getContext()).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    if (bitmap != null) {
                        float scale = bitmap.getHeight() / (float) bitmap.getWidth();
                        int width = getScreenWidth() - (getPaddingLeft() + getPaddingRight());
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                        drawable.addLevel(1, 1, bitmapDrawable);
                        drawable.setBounds(0, 0, width, (int) (width * scale));
                        drawable.setLevel(1);
                        invalidate();
                        setText(getText());
                    }
                }
            });
            return drawable;
        }
    }

    /**
     *
     */
    private class GraphicMixedClickableImage extends ClickableSpan {

        private int position;

        public GraphicMixedClickableImage(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View widget) {
            //点击图片后进行的操作
        }
    }

    /**
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     *
     * @return
     */
    public int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}
