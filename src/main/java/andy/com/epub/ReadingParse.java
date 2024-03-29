package andy.com.epub;

import com.google.gson.Gson;
import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class ReadingParse {

    public static void main(String[] args) {


        File file = new File("/Users/yangtingjun/Downloads/ALittlePrincess.epub");
        Map<String,Object> ret = new LinkedHashMap<>();

        InputStream in = null;
        try {
            //从输入流当中读取epub格式文件
            EpubReader reader = new EpubReader();
            in = new FileInputStream(file);
            Book book = reader.readEpub(in);
            //获取到书本的头部信息
            Metadata metadata = book.getMetadata();
            String title = book.getTitle();

            System.out.println("FirstTitle为："+title);

            ret.put("title",title);

            //获取到书本的全部资源
            Resources resources = book.getResources();
            System.out.println("所有资源数量为："+resources.size());
            //获取所有的资源数据
            Collection<String> allHrefs = resources.getAllHrefs();
            for (String href : allHrefs) {
                Resource resource = resources.getByHref(href);
                //data就是资源的内容数据，可能是css,html,图片等等
                byte[] data = resource.getData();
                String content = new String(data);
                // 获取到内容的类型  css,html,还是图片
                MediaType mediaType = resource.getMediaType();
            }
            //获取到书本的内容资源
            List<Resource> contents = book.getContents();
            System.out.println("内容资源数量为："+contents.size());
            //获取到书本的spine资源 线性排序
            Spine spine = book.getSpine();
            System.out.println("spine资源数量为："+spine.size());
            //通过spine获取所有的数据
            List<SpineReference> spineReferences = spine.getSpineReferences();
            for (SpineReference spineReference : spineReferences) {
                Resource resource = spineReference.getResource();
                //data就是资源的内容数据，可能是css,html,图片等等
                byte[] data = resource.getData();
                // 获取到内容的类型  css,html,还是图片
                MediaType mediaType = resource.getMediaType();
            }
            //获取到书本的目录资源
            TableOfContents tableOfContents = book.getTableOfContents();
            System.out.println("目录资源数量为："+tableOfContents.size());
            //获取到目录对应的资源数据
            List<TOCReference> tocReferences = tableOfContents.getTocReferences();


            List<Map<String,String>> contentList = new ArrayList<>();
            ret.put("content",contentList);


            for (TOCReference tocReference : tocReferences) {
                Resource resource = tocReference.getResource();
                String title1= tocReference.getTitle();

                System.out.println("title:"+title1);

                //data就是资源的内容数据，可能是css,html,图片等等
                byte[] data = resource.getData();
                String content = new String(data);

                String  finalText = Parser1.parse(content);


                Map<String,String> map = new HashMap<>();
                map.put("title",title1);
                map.put("content",finalText);

                contentList.add(map);

                // 获取到内容的类型  css,html,还是图片
                MediaType mediaType = resource.getMediaType();
                if(tocReference.getChildren().size()>0){
                    //获取子目录的内容
                }
            }


            Gson gson = new Gson();
            String jsonRet = gson.toJson(ret);

            FileUtils.writeStringToFile(new File("reading/"+title+".json"),jsonRet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //一定要关闭资源
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    static  class Parser1{
        public static String parse(String content){
            Document doc = Jsoup.parse(content);
            Elements elements = doc.select("p");

            List<String> eachText = elements.eachText();
            return eachText.stream().collect(Collectors.joining("\r\n"));
        }
    }

}
