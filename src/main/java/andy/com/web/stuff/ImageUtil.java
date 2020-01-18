package andy.com.web.stuff;


import org.junit.Test;
import java.util.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageUtil {

    // 字符集
    private static final String[] chars = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "人", "们", "百", "日", "美", "团",
            "的", "智", "能", "联", "网", "配", "送", "柜", "可", "以", "将", "餐", "食", "交", "付", "给",
            "通", "过", "手", "机", "应", "用", "预", "定", "外", "卖", "的", "顾", "客", "无", "人", "配",
            "送", "车", "能", "够", "自", "主", "穿", "行", "在", "拥", "挤", "而", "复", "杂", "的", "城",
            "市", "交", "通", "环", "境"};

    // 字符数量
    private static final int WORD_SIZE = 4;
    private static final int INTERUPT_LINES = 10;
    private static final int WIDTH = 120;
    private static final int HEIGHT = 60;
    private static final int FONT_SIZE = 30;

    /**
     * 生成验证码及图片
     * Object[0]：验证码字符串；
     * Object[1]：验证码图片。
     */
    public static Object[] createImage() {
        StringBuffer sb = new StringBuffer();
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = image.getGraphics();
        graphic.setColor(Color.LIGHT_GRAY);
        // 绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        Random ran = new Random();
        for (int i = 0; i < WORD_SIZE; i++) {
            int n = ran.nextInt(chars.length);
            graphic.setColor(getRandomColor());
            graphic.setFont(new Font(
                    null, Font.BOLD + Font.ITALIC, FONT_SIZE / 2 + 5 + new Random().nextInt(FONT_SIZE / 2)));
            graphic.drawString(
                    chars[n] + "", i * WIDTH / WORD_SIZE, HEIGHT * 1 / 3 + new Random().nextInt(HEIGHT * 2 / 3));
            sb.append(chars[n]);
        }
        //干扰线
        for (int i = 0; i < INTERUPT_LINES; i++) {
            graphic.setColor(getRandomColor());
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),
                    ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }
        return new Object[]{sb.toString(), image};
    }

    public static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
        return color;
    }


    public static String[] createBase64Image() throws IOException {
        Object[] objs = createImage();
        BufferedImage image = (BufferedImage) objs[1];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            String ret =  new String(Base64.getEncoder().encode(bytes));
            return new String[]{(String) (objs[0]), ret};
        } finally {
            baos.close();
            image = null;
            baos = null;
        }
    }

    @Test
    public void test1() throws IOException {
        Object[] objs = createImage();
        BufferedImage image = (BufferedImage) objs[1];
        OutputStream os = new FileOutputStream("1.png");
        ImageIO.write(image, "png", os);
        os.close();
    }

    @Test
    public void test2() throws IOException {
        Object[] objs = createBase64Image();
        System.out.println(String.format("%s  ,  %s", objs[0], objs[1]));
    }

}



