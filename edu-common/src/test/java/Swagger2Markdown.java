import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-01-08 15:37
 **/
public class Swagger2Markdown {

    @Test
    public void generateMarkdownFile() throws Exception {
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();
        URL apiUrl = new URL("http://localhost:8080/v2/api-docs");
        // 指定文件名称
        String markdownFileName = "/home/mola/IdeaProjects/edu/markdown/edu-server-api";
        Swagger2MarkupConverter.from(apiUrl)
                .withConfig(config)
                .build()
                //指定生成目录下生成指定文件
                .toFile(Paths.get(markdownFileName));
    }

    @Test
    public void time() throws InterruptedException {
        System.out.println(String.valueOf(System.currentTimeMillis()).substring(6));
        Thread.sleep(4000);
        System.out.println(System.currentTimeMillis());
    }
}
