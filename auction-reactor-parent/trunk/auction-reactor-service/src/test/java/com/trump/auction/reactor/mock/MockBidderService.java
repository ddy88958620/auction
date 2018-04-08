package com.trump.auction.reactor.mock;

import com.google.common.collect.ImmutableList;
import com.trump.auction.reactor.api.BidderService;
import com.trump.auction.reactor.api.model.Bidder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link BidderService} 模拟实现
 *
 * @author Owen
 * @since 2017/12/29
 */
@Service
public class MockBidderService implements BidderService {

    private AtomicInteger idGen = new AtomicInteger(10000);

    @Override
    public Bidder nextBidder() {
        return createRobot();
    }

    @Override
    public Bidder nextBidder(Bidder lastBidder, String auctionNo) {
        return nextBidder(lastBidder);
    }

    @Override
    public Bidder nextBidder(Bidder lastBidder) {
        Bidder robot = nextBidder();

        if (lastBidder == null) {
            return robot;
        }

        while (robot.equals(lastBidder)) {
            robot = nextBidder();
        }

        return robot;
    }

    private Bidder createRobot() {
        int index = nextNameIndex();

        Bidder robot = new Bidder();
        robot.setSubId(String.valueOf(idGen.get() + index));
        robot.setUserId("1");
        robot.setName(allName().get(index));
        robot.setAddrArea(nextArea());
        return robot;
    }

//    private String nextName() {
//        int length = 0;
//        while (length < 2) {
//            length = new Random().nextInt(6);
//        }
//
//        String start = "\\u4e00";
////        String end = "\\u9fa5";
//        int startIndex = Integer.parseInt(start.substring(2, start.length()), 16);
//        int endIndex = startIndex + 2000;
//
//        char[] nameArray = new char[length];
//        for (int i = 0; i < length; i++) {
//            nameArray[i] = (char)(new Random().nextInt(endIndex - startIndex) + startIndex);
//        }
//        return new String(nameArray);
//    }

    private int nextNameIndex() {
        return new Random().nextInt(allName().size());
    }

    private List<String> allName() {
        String[] names = new String[] {
                "半暮未凉",
                "夏有森光暖无疑",
                "雨巷",
                "风清淡雅",
                "漫天蒲公英",
                "夜雨飘雪",
                "白衬杉格子梦",
                "吾心给谁人",
                "青烟离歌",
                "夏日的綠色",
                "拢一蓑烟雨",
                "千城墨白",
                "倾城月光",
                "ら樱雪之城ペ",
                "白衣影眠梦",
                "花の物语",
                "青竹暖回阳",
                "冰泪紫茉",
                "悠然若雪",
                "眉蹙秋波",
                "天涯嘯西風",
                "迷乱花海",
                "倾城的美丽",
                "梦落轻寻",
                "静若繁花",
                "森林有鹿",
                "戏骨清风",
                "暖南绿倾i",
                "恍如夢境",
                "星辰美景",
                "夜梦萧寒",
                "醉魂愁梦相伴",
                "七颜初夏",
                "d调、樱花╰つ",
                "狂影秋风",
                "浅夏淡忆",
                "南薇",
                "时光恰如年",
                "耳边情话",
                "雨润静荷",
                "樱花﹨葬礼",
                "随梦而飞",
                "南蔷北薇",
                "森里伊人",
                "醉酒夢紅顏",
                "路尽隐香处",
                "森旅迷雾",
                "梦中旧人",
                "山涧晴岚",
                "望断江南岸",
                "剑舞天涯",
                "素笺淡墨",
                "星星滴蓝天",
                "≮梦之★情缘≯",
                "绿蕊★紫蓝",
                "笑颜百景",
                "清風挽離人",
                "柚夏",
                "捂风挽笑",
                "云末清廊",
                "森屿友人",
                "∫逝水无痕∫",
                "浮生醉清风",
                "倾城花音",
                "柚绿时光!",
                "倾城一笑醉红尘",
                "羽逸之光",
                "指名的幸福丶",
                "神锋暗杀队",
                "影丿魂战队",
                "兴城丶神之队",
                "t丿黔堂丨丶战队",
                "火速霹雳战队",
                "興峸丶神之队",
                "gpr梦幻之队",
                "魔夜战队",
                "大彩笔战队",
                "hd丶侠客队",
                "丿千年春丶梦之队",
                "咸鱼大队",
                "切菜战队",
                "王丨技术团队",
                "宠物恋灬梦之队",
                "燃烧嗜血大队",
                "☆轩殿战队★",
                "战魂★卫队",
                "幻魂丿队",
                "神话灬丿技术队",
                "影丿不死战队",
                "回龙星队",
                "华野突击小队",
                "丿无痕灬丨炫队",
                "完美灬女队",
                "rp℃战队",
                "雪儿灬盟队",
                "菟族丶零番队",
                "丿巅峰丶支队",
                "痞子丿敢死队",
                "vip丨巅峰队灬",
                "黑骑战队",
                "死亡★突击队",
                "may丶_猫队",
                "边境ご炫队",
                "s.y 战队",
                "不许败_战队丶",
                "秋明战队",
                "通天★战队",
                "河南灬国志队",
                "luck丶星队",
                "嗤古灬战队",
                "安安灬红队",
                "love厦门战队",
                "巛soso丶全队",
                "轩辕※团队",
                "狱队",
                "深夜丶黑衣队",
                "灭婊大队",
                "see丶神队",
                "幻丿突击队",
                "媚丨丶战队",
                "完美的战队",
                "梦兮ヽ花已落",
                "抹不掉的依赖",
                "记忆昰座荒岛",
                "梦醒、西楼",
                "删不掉思念",
                "掺泪情书",
                "烟消云散",
                "島是海哭碎菂訫",
                "续写忧伤",
                "回忆刺穿心脏╮",
                "眼角的淚光",
                "陌上花开迟",
                "旧城空念",
                "终于有始无终",
                "浅夏╭染指忧伤",
                "眼泪变成水蒸气",
                "〆泪湿衣襟",
                "情种多短命ζ",
                "晚风吻尽",
                "一个人旳荒岛",
                "流年丶染花落",
                "与孤独合葬",
                "苦涩的回忆",
                "眼泪里的鱼",
                "时光淡忘旧人心",
                "酒醉人心碎",
                "不可碰触的伤",
                "花谢忽如雪",
                "雾一样的忧伤",
                "浅笶≧掩饰泪光",
                "閉上眼ゝ說再見",
                "空城只因旧梦在",
                "浅场∵离别曲",
                "孤独陪葬我",
                "眼泪淋花",
                "眼泪〥为你流乾",
                "寡欢惨笑",
                "默言忆旧",
                "时光半落空城シ",
                "眼眸与伤",
                "欠你的泪滴",
                "未綄℡↘待續",
                "残城碎梦",
                "触不及的温柔",
                "梦醒梦碎",
                "残梦凉心",
                "呼吸都会痛",
                "想念终究是、想念",
                "指间滑过的忧伤",
                "花田流成泪海",
                "深巷裏的流浪貓"
        };

        return Arrays.asList(names);
    }

    private String nextArea() {
        return allArea().get(new Random().nextInt(allArea().size()));
    }

    private List<String> allArea() {
        return ImmutableList.of("上海上海", "北京北京", "江苏南京", "广东广州", "浙江杭州", "辽宁大连", "四川广安");
    }


    public static void main(String[] args) throws Exception {
        BidderService service = new MockBidderService();
        for (int i = 0; i < 100; i++) {
            System.out.println(service.nextBidder());
        }
    }
}
